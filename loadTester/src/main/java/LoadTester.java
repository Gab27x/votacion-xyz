import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import Demo.VotingTablePrx;

public class LoadTester {

    public static void main(String[] args) {
        File csvInput = new File("ciudadanos_mesa5.csv");
        File csvOutput = new File("resultados_mesa5.csv");

        long totalTime = 0;
        int voteCount = 0;

        try (Communicator communicator = Util.initialize(args, "LoadTester.cfg");
             BufferedReader reader = new BufferedReader(new FileReader(csvInput));
             BufferedWriter writer = new BufferedWriter(new FileWriter(csvOutput, false))) {

            VotingTablePrx tablePrx = VotingTablePrx.checkedCast(
                    communicator.propertyToProxy("VotingTable.Proxy"));

            if (tablePrx == null) {
                throw new RuntimeException("Failed to obtain proxy to VotingTable.");
            }

            String line;
            int i = 0;

            // Escribimos cabecera del archivo de salida
            writer.write("documento,candidatoId,resultado,tiempo_ms");
            writer.newLine();

            // Saltar cabecera del CSV de entrada
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 1) continue;

                String document = parts[0].trim();
                int candidateId = (i % 6) + 1;

                try {
                    long startTime = System.currentTimeMillis();

                    int result = tablePrx.vote(document, candidateId);

                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    totalTime += duration;
                    voteCount++;

                    writer.write(document + "," + candidateId + "," + result + "," + duration);
                    writer.newLine();
                } catch (Exception e) {
                    System.err.println("Fallo votando para: " + document);
                    writer.write(document + "," + candidateId + ",ERROR,0");
                    writer.newLine();
                }

                i++;
            }

            double averageTime = voteCount > 0 ? (double) totalTime / voteCount : 0;
            System.out.printf("Votación de carga completada. Resultados en: resultados_mesa5.csv%n");
            System.out.printf("Tiempo total: %d ms%n", totalTime);
            System.out.printf("Tiempo promedio por voto: %.2f ms%n", averageTime);

        } catch (IOException e) {
            System.err.println("Archivo CSV error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
