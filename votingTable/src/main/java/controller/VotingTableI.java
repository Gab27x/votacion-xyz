package controller;

import com.zeroc.Ice.Current;

import model.TableVote;

public class VotingTableI implements Demo.VotingTable {

    private final VotationController controller;

    public VotingTableI(VotationController controller) {
        this.controller = controller;
    }

    @Override
    public int vote(String document, int candidateId, Current current) {
        // Ya votó
        if (controller.getValidVoterIds().contains(document)) {
            return 2;
        }

        // Buscar mesa
        try {
            String mesa = controller.getReliableQuery().getVotingTableId(document);
            if (!mesa.equalsIgnoreCase(controller.getLocalTableId())) {
                return 1; 
            }
        } catch (Exception e) {
            return 3;
        }

        // Puede votar
        controller.sendVote(new TableVote(document, candidateId));
        return 0;
    }

}
