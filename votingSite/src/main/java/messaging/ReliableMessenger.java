package messaging;

import model.ReliableMessage;
import model.Vote;
import services.RMSender;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ReliableMessenger extends Thread {

    public static final String PENDING = "Pending";
    public static final String SENT = "Sent";

    private final Map<String, ReliableMessage> pendingMessages = new ConcurrentHashMap<>();
    private final Map<String, ReliableMessage> awaitingConfirmation = new ConcurrentHashMap<>();

    private long sequenceNumber = 0L;
    private final Object lock = new Object();
    private boolean active = true;
    private final RMSender rmSender;

    public ReliableMessenger(RMSender rmSender) {
        this.rmSender = rmSender;
    }

    public void addVote(Vote vote) {
        synchronized (lock) {
            String uuid = UUID.randomUUID().toString();
            ReliableMessage message = new ReliableMessage(uuid, sequenceNumber++, PENDING, vote);
            pendingMessages.put(uuid, message);
        }
    }

    public void confirmMessage(String uuid) {
        awaitingConfirmation.remove(uuid);
        System.out.println("[ReliableMessenger] Confirmed message: " + uuid);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        while (active) {
            for (Map.Entry<String, ReliableMessage> entry : pendingMessages.entrySet()) {
                try {
                    String uuid = entry.getKey();
                    ReliableMessage message = entry.getValue();

                    System.out.println("[ReliableMessenger] Sending vote with UUID: " + uuid);
                    rmSender.sendMessage(message.getVote(),null);

                    pendingMessages.remove(uuid);
                    awaitingConfirmation.put(uuid, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
