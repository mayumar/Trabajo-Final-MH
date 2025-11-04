package es.uma.informatica.misia.ae.simpleea.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import es.uma.informatica.misia.ae.simpleea.Problem;

public class KnapsackProblem implements Problem {
    
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

        } catch (IOException e){
            System.err.println("Error leyendo instancia: " + e.getMessage());
            System.exit(1);
        }
    }

}
