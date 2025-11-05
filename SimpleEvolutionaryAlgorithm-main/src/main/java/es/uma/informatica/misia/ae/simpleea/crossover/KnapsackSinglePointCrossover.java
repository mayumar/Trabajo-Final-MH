package es.uma.informatica.misia.ae.simpleea.crossover;

import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.Crossover;
import es.uma.informatica.misia.ae.simpleea.Individual;
import es.uma.informatica.misia.ae.simpleea.individuals.KnapsackIndividual;

public class KnapsackSinglePointCrossover implements Crossover {
	
	private Random rnd;
	
	public KnapsackSinglePointCrossover(Random rnd) {
		this.rnd=rnd;
	}

	@Override
	public KnapsackIndividual apply(Individual individual1, Individual individual2) {
		KnapsackIndividual parent1 = (KnapsackIndividual) individual1;
		KnapsackIndividual parent2 = (KnapsackIndividual) individual2;

        boolean[] g1 = parent1.getGenome();
        boolean[] g2 = parent2.getGenome();
        int length = g1.length;

        // Punto de corte
        int cutPoint = rnd.nextInt(length + 1);

        // Crear hijo
        KnapsackIndividual child = new KnapsackIndividual(length);
        boolean[] gc = child.getGenome();

        for (int i = 0; i < cutPoint; i++) {
            gc[i] = g1[i];
        }
        for (int i = cutPoint; i < length; i++) {
            gc[i] = g2[i];
        }

        return child;
	}

}