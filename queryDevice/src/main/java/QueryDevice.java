import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import BrokerIce.BrokerImpPrx;
import Query.QueryProxyIPrx;

public class QueryDevice implements Query.QueryDeviceI {

	public static QueryDeviceController queryDeviceController;

	@Override
	public String query(String id, Current current) {

		return queryDeviceController.query(id);
	}

	public static void main(String[] args) {

		try (Communicator communicator = Util.initialize(args, "QueryDevice.cfg")) {

			// exponer el servicio

			QueryDevice queryDevice = new QueryDevice();
			ObjectAdapter adapter = communicator.createObjectAdapter("QueryDevice");
			adapter.add(queryDevice, Util.stringToIdentity("QueryDevice"));
			adapter.activate();

			BrokerImpPrx brokerImpPrx = BrokerImpPrx.checkedCast(
					communicator.stringToProxy(communicator.getProperties().getProperty("Broker.Proxy")));

			if (brokerImpPrx == null) {
				throw new RuntimeException("El proxy del broker no implementa la interfaz esperada.");
			}

			String server = brokerImpPrx.getServer();

			QueryProxyIPrx queryProxyIPrx = QueryProxyIPrx.checkedCast(communicator.stringToProxy(server));

			System.out.println("Query device up and runnig");
			
			queryDeviceController = new QueryDeviceController(queryProxyIPrx);
			
			queryDeviceController.run();

			communicator.waitForShutdown();

		}

	}

}
