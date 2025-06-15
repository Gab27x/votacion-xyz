import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import BrokerIce.BrokerImpPrx;

public class QueryProxy implements Query.QueryProxyI {

	@Override
	public String getVotingTableById(String id, Current current) {
		return "llego";

	}

	public static void main(String[] args) {
		try (Communicator communicator = Util.initialize(args, "QueryProxy.cfg")) {

			QueryProxy queryProxy = new QueryProxy();
			ObjectAdapter adapter = communicator.createObjectAdapter("QueryProxy");

			ObjectPrx selfPrx = adapter.add(queryProxy, Util.stringToIdentity("QueryProxy")); // ya retorna el proxy
			adapter.activate();

			String proxyString = communicator.proxyToString(selfPrx);
			System.out.println("Mi proxy como string: " + proxyString);

			BrokerImpPrx brokerImpPrx = BrokerImpPrx.checkedCast(
				communicator.stringToProxy(communicator.getProperties().getProperty("Broker.Proxy"))
			);

			if (brokerImpPrx == null) {
				throw new RuntimeException("El proxy del broker no implementa la interfaz esperada.");
			}

			// Enviar el string al broker
			brokerImpPrx.addServer(proxyString);

			System.out.println("Proxy registrado en el broker con éxito.");

			// Esperar señal de apagado si quieres mantenerlo activo
			communicator.waitForShutdown();

		}

	}
}