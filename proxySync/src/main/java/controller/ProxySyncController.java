package controller;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import model.Vote;
import reliable.ReliableMessenger;
import reliableMessage.ACKServicePrx;
import repository.ProxySyncRepository;

public class ProxySyncController {
    private final ProxySyncRepository repository;
    private final ReliableMessenger messenger;

    public ProxySyncController(ProxySyncRepository repository, ReliableMessenger messenger) {
        this.repository = repository;
        this.messenger = messenger;
    }

    public void processVote(byte[] rmessage, ACKServicePrx ackService){

        try {
            //Deserializa el voto
            ByteArrayInputStream bis = new ByteArrayInputStream(rmessage);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Vote vote = (Vote) ois.readObject();

            repository.persistVote(vote);
            messenger.sendVote(vote, ackService);
            messenger.sendAck(vote.getVote(), ackService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
