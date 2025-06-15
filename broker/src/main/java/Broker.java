import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Broker implements BrokerIce.BrokerImp {

    private final List<String> servers = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public void addServer(String server, Current current) {
        if (server != null && !server.isEmpty()) {
            servers.add(server);
            System.out.println("Server added: " + server);
        }
    }

    @Override
    public String getServer(Current current) {
        synchronized (servers) {
            if (servers.isEmpty()) {
                return "NaN";
            }

            int i = index.getAndUpdate(n -> (n + 1) % servers.size());
            return servers.get(i);
        }
    }

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "Broker.cfg")) {

            Broker broker = new Broker();
            ObjectAdapter adapter = communicator.createObjectAdapter("Broker");
            adapter.add(broker, Util.stringToIdentity("Broker"));
            adapter.activate();

            System.out.println("Broker up and running");

            communicator.waitForShutdown();

        } catch (Exception e) {
            System.err.println("Broker failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
