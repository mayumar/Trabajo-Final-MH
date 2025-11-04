package es.uma.informatica.misia.ae.simpleea.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.Individual;
import es.uma.informatica.misia.ae.simpleea.Problem;
import es.uma.informatica.misia.ae.simpleea.individuals.KnapsackIndividual;

public class KnapsackProblem implements Problem {

    private int n;
    private int[] values;
    private int[] weights;
    private int capacity;
    
    public KnapsackProblem(String filename){
        loadInstance(filename);
    }

    private void loadInstance(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Saltar numero de problemas
            line = br.readLine();
            if (line == null) throw new IOException("Fichero vacío");

            // Leer cabecera del primer problema: n m óptimo
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length >= 3){
                    n = Integer.parseInt(parts[0]);
                    int m = Integer.parseInt(parts[1]);
                    // Solo usaremos el primer problema
                    break;
                }
            }

            values = new int[n];
            weights = new int[n];

            // Leer valores
            int i = 0;
            while (i < n) {
                line = br.readLine();
                if (line == null) throw new IOException("Datos incompletos");
                String[] parts = line.trim().split("\\s+");
                for (String p : parts) {
                    if (!p.isEmpty()) values[i++] = Integer.parseInt(p);
                    if (i == n) break;
                }
            }

            // Leer pesos
            i = 0;
            while (i < n) {
                line = br.readLine();
                if (line == null) throw new IOException("Datos incompletos");
                String[] parts = line.trim().split("\\s+");
                for (String p : parts){
                    if (!p.isEmpty()) weights[i++] = Integer.parseInt(p);
                    if (i == n) break;
                }
            }

            // Leer capacidad
            while ((line = br.readLine()) != null && line.trim().isEmpty());
            if (line == null) throw new IOException("Capacidad no encontrada");
            capacity = Integer.parseInt(line.trim().split("\\s+")[0]);

        } catch (IOException e){
            System.err.println("Error leyendo instancia: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public double evaluate(Individual individual) {
        KnapsackIndividual ind = (KnapsackIndividual) individual;
        boolean[] genome = ind.getGenome();

        int totalValue = 0;
        int totalWeight = 0;

        for (int i = 0; i < n; i++) {
            if (genome[i]) {
                totalValue += values[i];
                totalWeight += weights[i];
            }
        }

        // Penalización simple si excede la capacidad
        if (totalWeight > capacity) {
            int penalty = (totalWeight - capacity);
            ind.setFitness(totalValue - 1000.0 * penalty);
        } else {
            ind.setFitness(totalValue);
        }

        return ind.getFitness();
    }

    @Override
    public Individual generateRandomIndividual(Random rnd) {
        KnapsackIndividual ind = new KnapsackIndividual(n);
        for (int i = 0; i < n; i++) {
            ind.setGene(i, rnd.nextBoolean());
        }
        evaluate(ind);
        return ind;
    }

}
