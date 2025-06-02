package controller;

import messaging.ReliableMessenger;
import model.Vote;
public class VotingSiteController {
    private ReliableMessenger messenger;

    public VotingSiteController(ReliableMessenger messenger) {
        this.messenger = messenger;
    }

    public void receiveVote(Vote vote) {
        System.out.println("Recibido voto: " + vote);
        messenger.addVote(vote);
    }
}
