package reliable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import com.zeroc.Ice.Current;

import Demo.VotingTablePrx;
import model.ReliableMessage;
import model.Vote;
import reliableMessage.ACKService;
import reliableMessage.ACKServicePrx;
import reliableMessage.RMDestinationPrx;

public class ReliableMessenger implements ACKService {
    private RMDestinationPrx destination;

    public void setDestination(RMDestinationPrx destination) {
        this.destination = destination;
    }

    @Override
    public void ack(String messageId, Current current) {
        System.out.println("ACK recibido en ProxySync para msgId: " + messageId);
    }

    public void forwardMessage(ReliableMessage rmessage, ACKServicePrx ackService) {
        try {
            destination.reciveMessage(rmessage, ackService);
        } catch (Exception e) {
            System.err.println("Error reenviando mensaje: " + e.getMessage());
        }
    }

    public void sendAck(String messageId, ACKServicePrx ackService) {
        ackService.ack(messageId);
    }

    public void sendVote(Vote vote, ACKServicePrx ackService) {
        try {
            String uuid = UUID.randomUUID().toString();
            long numberMessage = System.currentTimeMillis();
            String state = "vote";

            ReliableMessage rmessage = new ReliableMessage(uuid, numberMessage, state, vote);

            forwardMessage(rmessage, ackService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

