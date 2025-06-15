import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import Query.queryProxy;

public class QueryProxy implements Query.queryProxy {

	@Override
	public String getVotingTableById(String id, Current current) {
		return "llego";

	}

	public static void main(String[] args) {
		try (Communicator communicator = Util.initialize(args, "QueryProxy.cfg")) {

			// objeto prx

			QueryProxy queryProxy = new QueryProxy();
			ObjectAdapter adapter = communicator.createObjectAdapter("QueryProxy");

			adapter.add(queryProxy, Util.stringToIdentity("QueryProxy"));
			adapter.activate();
		}

	}
}