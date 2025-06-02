package messaging;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import model.Vote;
import model.ReliableMessage;
import communication.Notification;

public class RMJob extends Thread {

    public static final String PENDING = "Pending";
    public static final String SENDED = "Sended";

    private final Map<String, ReliableMessage> messagesPending = new ConcurrentHashMap<>();
    private final Map<String, ReliableMessage> forConfirm = new ConcurrentHashMap<>();

    private long sequenceNumber = 0L;
    private final Object lock = new Object();
    private boolean enabled = true;

    private final Notification notification;

    public RMJob(Notification notification) {
        this.notification = notification;
    }

    public void add(Vote vote) {
        synchronized (lock) {
            ReliableMessage relMessage = new ReliableMessage(
                UUID.randomUUID().toString(),
                sequenceNumber++,
                PENDING,
                vote
            );
            messagesPending.put(relMessage.getUuid(), relMessage);
        }
    }

    public void confirmMessage(String uuid) {
        forConfirm.remove(uuid);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void run() {
        while (enabled) {
            for (Map.Entry<String, ReliableMessage> entry : messagesPending.entrySet()) {
                try {
                    System.out.println("Sending vote reliably with UUID: " + entry.getKey());
                    notification.sendMessage(entry.getValue());
                    messagesPending.remove(entry.getKey());
                    forConfirm.put(entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    System.err.println("Error sending vote reliably: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(10000); // 10 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
