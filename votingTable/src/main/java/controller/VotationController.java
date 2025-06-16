package controller;

import reliableMessage.RMSourcePrx;

import java.util.Arrays;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import Reliable.ReliableQueryPrx;
import model.TableVote;
import model.Vote;

import userInterface.Cli;

public class VotationController implements Runnable {

    private Cli cli;
    private RMSourcePrx sitePrx; // Proxy a VotingSite remoto(reliableServer)
    private ReliableQueryPrx reliableQuery;
    private String localTableId;
    private List<String> validVoterIds = Arrays.asList("123", "456", "789"); //Datos de prueba, no juzgar
    

    

    public VotationController(RMSourcePrx sitePrx, ReliableQueryPrx reliableQuery, Communicator communicator) {
        this.cli = new Cli(this);
        this.sitePrx = sitePrx;
        this.reliableQuery = reliableQuery; 
        this.localTableId = communicator.getProperties().getProperty("Table.Id");
    }

    @Override
    public void run() {
        System.out.println("VotationController: Inicializando...");
        cli.startVotingProcess();
        System.out.println("VotationController: Finalizado.");
    }

    public String[] getCandidates() {
        return new String[]{"Gabriel", "Juan", "Rony"};
    }

    public boolean canVoteHere(String voterId) {
        if (validVoterIds.contains(voterId)) {
            System.out.println("Voter ID no válido localmente.");
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

        // Llama al proxy remoto de VotingSite(reliableServer) (que actúa como RMSource)
        try {
            sitePrx.sendMessage(new Vote(String.valueOf(vote.getCandidateId())));
            System.out.println("Voto enviado al VotingSite.");

            validVoterIds.add(vote.getId());//Agrega al array

        } catch (Exception e) {
            System.err.println("Error enviando voto a VotingSite: " + e.getMessage());
        }
    }
}
