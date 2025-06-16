import java.sql.Connection;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import utils.ConexionDB;
import utils.DAO;

public class QueryServer implements Query.QueryServerI {

	@Override
	public String getVotingTableById(String id, Current current) {
		System.out.println("query from: " + id);
		try (
				Connection conn = ConexionDB.conectar()) {
			DAO dao = new DAO(conn);
			Integer mesaId = dao.obtenerMesaIdPorDocumento(id);
			return mesaId != null ? String.valueOf(mesaId) : "No encontrado";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error al consultar";
		}
	}

	public static void main(String[] args) {
		String cfg = args.length > 0 ? args[0] : "QueryServer.cfg";

		try (Communicator communicator = Util.initialize(args, cfg)) {
			QueryServer server = new QueryServer();

			ObjectAdapter adapter = communicator.createObjectAdapter("QueryServer");
			adapter.add(server, Util.stringToIdentity("QueryServer"));
			adapter.activate();
			System.out.println("QueryServer up ...");

			communicator.waitForShutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConexionDB.cerrarPool(); // importante si usas Hikari
		}
	}
}
