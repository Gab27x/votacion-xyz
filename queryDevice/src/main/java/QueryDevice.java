import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import Query.QueryProxyIPrx;


public class QueryDevice implements Query.QueryDeviceI {

	public static QueryDeviceController queryDeviceController;

	@Override
	public String query(String id, Current current) {

		return queryDeviceController.query(id);
	}

	public static void main(String[] args) {

		try (Communicator communicator = Util.initialize(args, "QueryDevice.cfg")) {
		


			// objeto prx


			// exponer el servicio

			QueryDevice queryDevice = new QueryDevice();
			ObjectAdapter adapter = communicator.createObjectAdapter("QueryDevice");
			adapter.add(queryDevice, Util.stringToIdentity("QueryDevice"));
			adapter.activate();
			
			System.out.println("Query device up and runnig");

			queryDeviceController.run();
			
			communicator.waitForShutdown();

		}

	}

}
