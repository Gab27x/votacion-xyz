import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class QueryServer implements Query.QueryServerI {

	@Override
	public String getVotingTableById(String id, Current current) {
		if (id.equalsIgnoreCase("1"))
			return "vaya vote a icesi";
		else
			return "no esta registrado";
	}

	public static void main(String[] args) {

		try (Communicator communicator = Util.initialize(args, "QueryServer.cfg")) {


			QueryServer server = new QueryServer();

			ObjectAdapter adapter = communicator.createObjectAdapter("QueryServer");
			adapter.add(server, Util.stringToIdentity("QueryServer"));
			adapter.activate();
			System.out.println("QueryServer up ...");


			communicator.waitForShutdown();
		}
	}

}
