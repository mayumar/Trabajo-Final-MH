package es.uma.informatica.misia.ae.simpleea;

public class RunResult {
    public final double bestFitness;
    public final boolean foundOptimal;
    public final int functionEvaluations;
    public final long elapsedMillis;

    public RunResult(double bestFitness, boolean foundOptimal, int functionEvaluations, long elapsedMillis) {
        this.bestFitness = bestFitness;
        this.foundOptimal = foundOptimal;
        this.functionEvaluations = functionEvaluations;
        this.elapsedMillis = elapsedMillis;
    }
}
