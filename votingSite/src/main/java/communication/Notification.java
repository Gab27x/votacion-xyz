package communication;

import model.ReliableMessage;
import model.Vote;
import messageReliable.RMDestinationPrx;
import messageReliable.ACKServicePrx;

public class Notification {

    private RMDestinationPrx service;
    private ACKServicePrx ackService;

    public void setAckService(ACKServicePrx ackService) {
        this.ackService = ackService;
    }

    public void setService(RMDestinationPrx service) {
        this.service = service;
    }

    public void sendMessage(ReliableMessage message) {
        Vote vote = message.getVote();
        service.reciveMessage(vote, ackService);
    }
}

