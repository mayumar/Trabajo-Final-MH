package es.uma.informatica.misia.ae.simpleea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.problems.KnapsackProblem;

public class EvolutionaryAlgorithm {
	public static final String MAX_FUNCTION_EVALUATIONS_PARAM = "maxFunctionEvaluations";
	public static final String RANDOM_SEED_PARAM = "randomSeed";
	public static final String POPULATION_SIZE_PARAM = "populationSize";
	public static final String STOP_MODE_PARAM = "stopMode";	// valores: 0 = max evaluations, 1 = stop at optimal
	
	private Problem problem;
	private int functionEvaluations;
	private int maxFunctionEvaluations;
	private List<Individual> population;
	private int populationSize;
	private Random rnd;
	private int stopMode;
	
	private Individual bestSolution;
	
	private Selection selection;
	private Replacement replacement;
	private Mutation mutation;
	private Crossover recombination;

	private boolean foundOptimal = false;
	private long elapsedTimeMillis = 0;
	
	public EvolutionaryAlgorithm(Map<String, Double> parameters, Problem problem) {
		configureAlgorithm(parameters, problem);
	}
	
	private void configureAlgorithm(Map<String, Double> parameters, Problem problem) {
		populationSize = parameters.get(POPULATION_SIZE_PARAM).intValue();
		maxFunctionEvaluations = parameters.get(MAX_FUNCTION_EVALUATIONS_PARAM).intValue();
		double bitFlipProb = parameters.get(BitFlipMutation.BIT_FLIP_PROBABILITY_PARAM);
		double singlePointProb = parameters.get(SinglePointCrossover.SINGLE_POINT_PROBABILITY_PARAM);
		long randomSeed = parameters.get(RANDOM_SEED_PARAM).longValue();
		int stopMode = parameters.get(STOP_MODE_PARAM).intValue();
		
		this.problem = problem; 
		this.stopMode = stopMode;
		
		rnd = new Random(randomSeed);
		
		selection = new BinaryTournament(rnd);
		replacement = new ElitistReplacement();
		mutation = new BitFlipMutation(rnd, bitFlipProb);
		recombination = new SinglePointCrossover(rnd, singlePointProb);
	}
	
	public Individual run() {
		long t0 = System.currentTimeMillis();

		population = generateInitialPopulation();
		functionEvaluations = 0;
		foundOptimal = false;
		
		evaluatePopulation(population);

		// Recuperamos el optimo, si existe
		double optimal = 0.0;
		if (problem instanceof KnapsackProblem) {
			optimal = ((KnapsackProblem) problem).getOptimal();
		}
		while (functionEvaluations < maxFunctionEvaluations) {
			// Si estamos en modo 1, tenemos optimo conocido y ya lo hemos alcanzado, paramos
			if (stopMode == 1) {
				if (optimal > 0 && bestSolution != null && bestSolution.getFitness() >= optimal) {
					foundOptimal = true;
					break;
				}
			}

			// Algoritmo evolutivo estandar
			Individual parent1 = selection.selectParent(population);
			Individual parent2 = selection.selectParent(population);
			Individual child = recombination.apply(parent1, parent2);
			child = mutation.apply(child);
			evaluateIndividual(child);
			population = replacement.replacement(population, Arrays.asList(child));
		}

		long t1 = System.currentTimeMillis();
		elapsedTimeMillis = t1 - t0;
		
		return bestSolution;
	}
	
	private void evaluateIndividual(Individual individual) {
		double fitness = problem.evaluate(individual);
		individual.setFitness(fitness);
		functionEvaluations++;
		checkIfBest(individual);
	}

	private void checkIfBest(Individual individual) {
		if (bestSolution == null || individual.getFitness()> bestSolution.getFitness()) {
			bestSolution = individual;
		}
	}

	private void evaluatePopulation(List<Individual> population) {
		for (Individual individual: population) {
			evaluateIndividual(individual);
		}
	}

	private List<Individual> generateInitialPopulation() {
		List<Individual> population = new ArrayList<>();
		for (int i=0; i < populationSize; i++) {
			population.add(problem.generateRandomIndividual(rnd));
		}
		return population;
	}

	public boolean hasFoundOptimal() {
		return foundOptimal;
	}

	public int getFunctionEvaluations() {
		return functionEvaluations;
	}

	public long getElapsedTimeMillis() {
		return elapsedTimeMillis;
	}
	
}
