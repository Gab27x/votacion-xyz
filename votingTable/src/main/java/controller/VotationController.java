package controller;

import Demo.VotingSitePrx;
import model.Vote;
import userInterface.Cli;

public class VotationController implements Runnable {

    private Cli cli;
    private VotingSitePrx sitePrx; // Proxy a VotingSite remoto

    public VotationController(VotingSitePrx sitePrx) {
        this.cli = new Cli(this);
        this.sitePrx = sitePrx;
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

    public void sendVote(Vote vote) {
        System.out.println("VotationController: Voto recibido: " + vote.getId() + " para candidato ID: " + vote.getCandidateId());

        // Llama al proxy remoto de VotingSite (que actúa como RMSource)
        try {
            sitePrx.send(vote.getCandidateId());
            System.out.println("Voto enviado al VotingSite.");
        } catch (Exception e) {
            System.err.println("Error enviando voto a VotingSite: " + e.getMessage());
        }
    }
}
