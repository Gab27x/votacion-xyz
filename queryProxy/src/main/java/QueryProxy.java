import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import BrokerIce.BrokerImpPrx;
import Query.QueryServerIPrx;

public class QueryProxy implements Query.QueryProxyI {

	private static QueryServerIPrx queryServerIPrx;

	@Override
	public String getVotingTableById(String id, Current current) {
		System.out.println("request from: " + id);

		return queryServerIPrx.getVotingTableById(id);

	}

	public static void main(String[] args) {
		String cfg = args.length > 0 ? args[0] : "QueryProxy.cfg";
		try (Communicator communicator = Util.initialize(args, cfg)) {
			QueryProxy queryProxy = new QueryProxy();
			ObjectAdapter adapter = communicator.createObjectAdapter("QueryProxy");

			ObjectPrx selfPrx = adapter.add(queryProxy, Util.stringToIdentity("QueryProxy")); // ya retorna el proxy
			adapter.activate();

			String proxyString = communicator.proxyToString(selfPrx);
			System.out.println("Mi proxy como string: " + proxyString);

			BrokerImpPrx brokerImpPrx = BrokerImpPrx.checkedCast(
					communicator.stringToProxy(communicator.getProperties().getProperty("Broker.Proxy")));

			if (brokerImpPrx == null) {
				throw new RuntimeException("El proxy del broker no implementa la interfaz esperada.");
			}

			// Enviar el string al broker
			brokerImpPrx.addServer(proxyString);

			System.out.println("Proxy registrado en el broker con éxito.");

			queryServerIPrx = QueryServerIPrx
					.checkedCast(
							communicator.stringToProxy(communicator.getProperties().getProperty("QueryServer.Proxy")));

			if (queryServerIPrx == null)
				throw new RuntimeException("fail query serverprx");
			// Esperar señal de apagado si quieres mantenerlo activo

			
			communicator.waitForShutdown();

		}

	}
}