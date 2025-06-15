import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import BrokerIce.BrokerImp;

public class Broker implements BrokerIce.BrokerImp {

	private final List<String> servers = new ArrayList<>();

	@Override
	public synchronized void addServer(String server, Current current) {
		System.out.println(server);
		if (server != null && !server.isEmpty()) {
			servers.add(server);
			System.out.println("Server added: " + server);
		}
	}

	@Override
	public synchronized String getServer(Current current) {
		if (servers.isEmpty()) {
			return "NaN";
		}
		return servers.get(0);
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
