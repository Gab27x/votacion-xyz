package services;

import com.zeroc.Ice.Current;
import model.Vote;
import model.ReliableMessage;
import messageReliable.RMDestinationPrx;
import messageReliable.ACKServicePrx;
import messageReliable.RMSource;
import communication.Notification;
import messaging.RMJob;

public class RMSender implements RMSource {

    private RMJob jobM;
    private Notification notification;

    public RMSender(RMJob job, Notification not) {
        this.jobM = job;
        this.notification = not;
    }

    @Override
    public void sendMessage(model.Vote vote, Current current) {
        jobM.add(vote);
    }

    @Override
    public void setServerProxy(RMDestinationPrx destination, Current current) {
        notification.setService(destination);
    }
}
