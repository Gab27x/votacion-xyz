import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import Demo.VotingTablePrx;
import controller.ProxySyncController;
import reliable.ReliableMessenger;
import reliable.ReliableReceiver;
import reliableMessage.RMDestinationPrx;
import repository.ProxySyncRepository;

public class ProxySync {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "ProxySync.cfg")) {

            ObjectAdapter adapter = communicator.createObjectAdapter("ProxySync");

            ProxySyncRepository repo = new ProxySyncRepository("proxySyncdb.txt");
            ReliableMessenger messenger = new ReliableMessenger();
            ProxySyncController controller = new ProxySyncController(repo, messenger);
            ReliableReceiver receiver = new ReliableReceiver(controller, messenger, repo);

            //Adapter registro
            adapter.add(receiver, Util.stringToIdentity("Receiver"));
            adapter.add(messenger, Util.stringToIdentity("AckService"));

            adapter.activate();

            ObjectPrx serverPrx = communicator.stringToProxy("Service:tcp -h localhost -p 10012");
            messenger.setDestination(RMDestinationPrx.checkedCast(serverPrx));

            communicator.waitForShutdown();
        }
    }
}
