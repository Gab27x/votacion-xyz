package communication;

import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import messageReliable.ACKServicePrx;
import messageReliable.RMDestinationPrx;
import model.ReliableMessage;
import model.Vote;

public class Notification {

    private RMDestinationPrx service;
    private ACKServicePrx ackService;

    public Notification(ObjectAdapter adapter) {
        AckServiceImpl ackServiceImpl = new AckServiceImpl();
        this.ackService = messageReliable.ACKServicePrx.checkedCast(
                adapter.addWithUUID(ackServiceImpl)
        );
    }

    public void setService(RMDestinationPrx service) {
        this.service = service;
    }

    public void sendMessage(ReliableMessage message) {
        Vote vote = message.getVote();
        service.reciveMessage(vote, ackService);
    }
}
