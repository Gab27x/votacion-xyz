package controller;

import reliableMessage.RMSourcePrx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import Reliable.ReliableQueryPrx;
import model.TableVote;
import model.Vote;

import userInterface.Cli;

public class VotationController implements Runnable {

    private static final String FILE_PATH = "votantesRegistrados.csv";

    private Cli cli;
    private RMSourcePrx sitePrx;
    private ReliableQueryPrx reliableQuery;
    private String localTableId;
    private List<String> validVoterIds;

    public VotationController(RMSourcePrx sitePrx, ReliableQueryPrx reliableQuery, Communicator communicator) {
        this.cli = new Cli(this);
        this.sitePrx = sitePrx;
        this.reliableQuery = reliableQuery;
        this.localTableId = communicator.getProperties().getProperty("Table.Id");

        // Carga los votantes desde el archivo
        this.validVoterIds = loadVoterIdsFromFile();
    }

    @Override
    public void run() {
        System.out.println("VotationController: Inicializando...");
        cli.startVotingProcess();
        System.out.println("VotationController: Finalizado.");
    }

    public String[] getCandidates() {
        return new String[]{"Gabriel Tamura", "Alejandro Muñoz", "Kevin Rodriguez", "Domiciano Rincon", "Marlon Gomez", "Santiago Gutierrez"};
    }

    public List<String> getValidVoterIds() {
        return validVoterIds;
    }

    public ReliableQueryPrx getReliableQuery() {
        return reliableQuery;
    }

    public String getLocalTableId() {
        return localTableId;
    }

    public boolean canVoteHere(String voterId) {
        if (validVoterIds.contains(voterId)) {
            System.out.println("Ya ha votado.");
            return false;
        }

        try {
            String mesaDelVotante = reliableQuery.getVotingTableId(voterId);
            System.out.println("Mesa asignada al votante: " + mesaDelVotante);
            return mesaDelVotante.equalsIgnoreCase(localTableId);
        } catch (Exception e) {
            System.err.println("Error contactando con ReliableServer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void sendVote(TableVote vote) {
        System.out.println("VotationController: Voto recibido: " + vote.getId() + " para candidato ID: " + vote.getCandidateId());

        try {
            sitePrx.sendMessage(new Vote(String.valueOf(vote.getCandidateId())));
            System.out.println("Voto enviado al VotingSite.");

            validVoterIds.add(vote.getId());
            saveVoterIdToFile(vote.getId()); // Guarda en disco

        } catch (Exception e) {
            System.err.println("Error enviando voto a VotingSite: " + e.getMessage());
        }
    }

    private List<String> loadVoterIdsFromFile() {
        List<String> ids = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return ids;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Saltar cabecera si hay
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                ids.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error leyendo votantes registrados: " + e.getMessage());
        }

        return ids;
    }

    private void saveVoterIdToFile(String voterId) {
        try {
            File file = new File(FILE_PATH);
            boolean isNewFile = !file.exists();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (isNewFile) {
                    writer.write("documento");
                    writer.newLine();
                }

                writer.write(voterId);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando votante en archivo: " + e.getMessage());
        }
    }
}
