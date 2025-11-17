package es.uma.informatica.misia.ae.simpleea;

import java.util.HashMap;
import java.util.Map;

import es.uma.informatica.misia.ae.simpleea.problems.KnapsackProblem;


public class Main {
	
	public static void main (String args []) {
		
		if (args.length < 7) {
			System.err.println("Usage: <population size> <function evaluations> <singlepoint probability> <bitflip probability> <stopMode> <random seed>");
            System.err.println("mode: 0 = max evaluations, 1 = stop at optimal");
            return;
        }
		
		Map<String, Double> parameters = readEAParameters(args);
		
		Problem problem = new KnapsackProblem("SimpleEvolutionaryAlgorithm-main/data/mknap1.txt"); // "SimpleEvolutionaryAlgorithm-main/data/mknap1.txt"
		EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(parameters, problem);
		
		long t0 = System.currentTimeMillis();
		Individual bestSolution = evolutionaryAlgorithm.run();
		long t1 = System.currentTimeMillis();
		
		// CSV line:
        // seed,mode,popsize,maxEvals,bitflip,crossover,bestFitness,foundOptimal,evaluations,elapsedMillis
        System.out.printf("%d,%s,%d,%d,%.6f,%.6f,%.6f,%b,%d,%d\n",
            parameters.get(EvolutionaryAlgorithm.RANDOM_SEED_PARAM),
			parameters.get(EvolutionaryAlgorithm.STOP_MODE_PARAM),
            parameters.get(EvolutionaryAlgorithm.POPULATION_SIZE_PARAM).intValue(),
            parameters.get(EvolutionaryAlgorithm.MAX_FUNCTION_EVALUATIONS_PARAM).intValue(),
            parameters.get(BitFlipMutation.BIT_FLIP_PROBABILITY_PARAM),
            parameters.get(SinglePointCrossover.SINGLE_POINT_PROBABILITY_PARAM),
            bestSolution.getFitness(),
            evolutionaryAlgorithm.hasFoundOptimal(),
            evolutionaryAlgorithm.getFunctionEvaluations(),
            evolutionaryAlgorithm.getElapsedTimeMillis()
        );
	}

	private static Map<String, Double> readEAParameters(String[] args) {
		Map<String, Double> parameters = new HashMap<>();
		parameters.put(EvolutionaryAlgorithm.POPULATION_SIZE_PARAM, Double.parseDouble(args[0]));
		parameters.put(EvolutionaryAlgorithm.MAX_FUNCTION_EVALUATIONS_PARAM, Double.parseDouble(args[1]));
		parameters.put(SinglePointCrossover.SINGLE_POINT_PROBABILITY_PARAM, Double.parseDouble(args[2]));
		parameters.put(BitFlipMutation.BIT_FLIP_PROBABILITY_PARAM, Double.parseDouble(args[3]));
		parameters.put(EvolutionaryAlgorithm.STOP_MODE_PARAM, Double.parseDouble(args[4]));

		long randomSeed = System.currentTimeMillis();
		if (args.length > 4) {
			randomSeed = Long.parseLong(args[5]);
		}
		parameters.put(EvolutionaryAlgorithm.RANDOM_SEED_PARAM, (double)randomSeed);
		return parameters;
	}

}
