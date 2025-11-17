package es.uma.informatica.misia.ae.simpleea.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import es.uma.informatica.misia.ae.simpleea.BinaryString;
import es.uma.informatica.misia.ae.simpleea.Individual;
import es.uma.informatica.misia.ae.simpleea.Problem;

public class KnapsackProblem implements Problem {

    private int n;
    private int m;
    private double optimal;
    private double[] p;         // tamaño n
    private double[][] r;       // tamaño m x n
    private double[] b;         // tamaño m
    
    public KnapsackProblem(String filename){
        loadProblem(filename);
    }

    public double getOptimal() {
        return this.optimal;
    }

    private void loadProblem(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            // Leer y descartar K (primer número del archivo)
            String line = br.readLine();
            while (line != null && line.trim().isEmpty()) {
                line = br.readLine(); // saltar líneas vacías
            }

            // ---- Leer n, m y valor óptimo ----
            line = br.readLine();
            while (line != null && line.trim().isEmpty()) {
                line = br.readLine();
            }

            StringTokenizer st = new StringTokenizer(line);

            this.n = Integer.parseInt(st.nextToken());
            this.m = Integer.parseInt(st.nextToken());
            this.optimal = Integer.parseInt(st.nextToken());
            
            // ---- Leer p(j) ----
            this.p = readDoubleArray(br, this.n);

            // ---- Leer matriz r(i,j) ----
            this.r = new double[this.m][this.n];
            for (int i = 0; i < this.m; i++) {
                this.r[i] = readDoubleArray(br, this.n);
            }

            // ---- Leer b(i) ----
            this.b = readDoubleArray(br, this.m);
            
            
        } catch (IOException e){
            System.err.println("Error leyendo instancia: " + e.getMessage());
            System.exit(1);
        }
    }
    
    @Override
    public double evaluate(Individual individual) {
        BinaryString ind = (BinaryString) individual;
        byte[] x = ind.getChromosome();
        
        // Calcular beneficio total
        double beneficio = 0;
        for (int j = 0; j < this.n; j++){
            if (x[j] == 1) {
                beneficio += this.p[j];
            }
        }

        // Comprobar restricciones
        for (int i = 0; i < this.m; i++) {
            double suma = 0;
            for (int j = 0; j < n; j++) {
                if (x[j] == 1) {
                    suma += r[i][j];
                }
            }
            if (suma > b[i]) {
                return -1e6; // solucion no factible -> fitness muy bajo
            }
        }

        // Si todo ok, el fitnes es el beneficio
        return beneficio;
    }

    @Override
    public Individual generateRandomIndividual(Random rnd) {
        return new BinaryString(n,rnd);
    }

    // Función auxiliar para leer una línea con N dobles
    private static double[] readDoubleArray(BufferedReader br, int size) throws IOException {

        String line = br.readLine();

        // Saltar líneas vacías o nulas
        while (line != null && line.trim().isEmpty()) {
            line = br.readLine();
        }

        if (line == null) {
            throw new IOException("Error: línea inesperadamente vacía al leer datos del problema.");
        }

        StringTokenizer st = new StringTokenizer(line);

        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            if (!st.hasMoreTokens()) {
                throw new IOException("Error: no hay suficientes números en la línea: " + line);
            }
            arr[i] = Double.parseDouble(st.nextToken());
        }

        return arr;
    }

}
