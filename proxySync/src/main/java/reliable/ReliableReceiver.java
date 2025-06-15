package reliable;

import com.zeroc.Ice.Current;

import controller.ProxySyncController;
import model.ReliableMessage;
import reliableMessage.ACKServicePrx;
import reliableMessage.RMDestination;
import repository.ProxySyncRepository;

public class ReliableReceiver implements RMDestination {
    private final ProxySyncController controller;
    private final ProxySyncRepository repository;
    private final ReliableMessenger messenger;



    public ReliableReceiver(ProxySyncController controller, ReliableMessenger messenger, ProxySyncRepository repository) {
        this.controller = controller;
        this.repository = repository;
        this.messenger = messenger;
    }

    @Override
    public void reciveMessage(ReliableMessage rmessage, ACKServicePrx ackService, Current current) {
        System.out.println("Proxy recibió mensaje con UUID: " + rmessage.getUuid());
        repository.save(rmessage);
        messenger.forwardMessage(rmessage, ackService);
    }
}
