package es.uma.informatica.misia.ae.simpleea.individuals;

import es.uma.informatica.misia.ae.simpleea.Individual;

public class KnapsackIndividual extends Individual{
    
    private boolean[] genome;

    public KnapsackIndividual(int n) {
        this.genome = new boolean[n];
    }

    public boolean[] getGenome() {
        return this.genome;
    }

    public void setGenome(boolean[] genome) {
        this.genome = genome;
    }

    public int length() {
        return this.genome.length;
    }

    public void setGene(int i, boolean value) {
        this.genome[i] = value;
    }

    public boolean getGene(int i) {
        return this.genome[i];
    }
}
