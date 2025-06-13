package services;

import com.zeroc.Ice.Current;

import communication.Notification;
import model.Vote;
import reliableMessage.RMDestinationPrx;
import reliableMessage.RMSource;
import threads.RMJob;

public class RMSender implements RMSource {

    private RMJob jobM;
    private Notification notification;

    public RMSender(RMJob job, Notification not) {
        notification = not;
        jobM = job;
    }

    @Override
    public void sendMessage(Vote vote, Current current) {
        jobM.add(vote);
    }

    @Override
    public void setServerProxy(RMDestinationPrx destination, Current current) {
        notification.setService(destination);
    }
}
