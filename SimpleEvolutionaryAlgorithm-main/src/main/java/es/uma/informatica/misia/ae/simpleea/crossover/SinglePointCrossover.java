package es.uma.informatica.misia.ae.simpleea.crossover;

import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.Crossover;
import es.uma.informatica.misia.ae.simpleea.Individual;
import es.uma.informatica.misia.ae.simpleea.individuals.BinaryString;

public class SinglePointCrossover implements Crossover {
	
	private Random rnd;
	
	public SinglePointCrossover(Random rnd) {
		this.rnd=rnd;
	}

	@Override
	public BinaryString apply(Individual individual1, Individual individual2) {
		BinaryString binaryParent1 = (BinaryString) individual1;
		BinaryString binaryParent2 = (BinaryString) individual2;
		
		BinaryString child = new BinaryString (binaryParent1);
		int cutPoint = rnd.nextInt(binaryParent1.getChromosome().length+1);
		
		for (int i=cutPoint; i < binaryParent1.getChromosome().length; i++) {
			child.getChromosome()[i] = binaryParent2.getChromosome()[i];
		}
		return child;
	}

}
