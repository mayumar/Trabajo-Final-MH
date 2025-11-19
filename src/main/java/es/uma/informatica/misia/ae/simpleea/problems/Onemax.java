package es.uma.informatica.misia.ae.simpleea.problems;

import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.BinaryString;
import es.uma.informatica.misia.ae.simpleea.Individual;
import es.uma.informatica.misia.ae.simpleea.Problem;

public class Onemax implements Problem{
	private int n;
	
	public Onemax(int n) {
		this.n=n;
	}

	public double evaluate(Individual individual) {
		BinaryString binaryString = (BinaryString)individual;
		double result = 0.0;
		for (int i=0; i < binaryString.getChromosome().length; i++) {
			result += binaryString.getChromosome()[i];
		}
		return result;
	}
	
	public BinaryString generateRandomIndividual(Random rnd) {
		return new BinaryString(n,rnd);
	}

}
