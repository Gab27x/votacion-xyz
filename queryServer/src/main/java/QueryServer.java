
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import java.sql.Connection;



public class QueryServer implements Query.QueryServerI {

	private DAO dao;

	public QueryServer(DAO dao) {
		this.dao = dao;
	}

	@Override
	public String getVotingTableById(String id, Current current) {
		System.out.println("query from: " + id);
		try {
			// aquí podrías parsear el id si es necesario
			int idInt = Integer.parseInt(id);
			String resultado = dao.obtenerInfoCiudadanoPorId(idInt);
			return resultado != null ? resultado : "no encontrado";
		} catch (NumberFormatException e) {
			return "ID inválido";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error al consultar";
		}
	}

	public static void main(String[] args) {
		String cfg = args.length > 0 ? args[0] : "QueryServer.cfg";

		try (
			Connection conexion = ConexionDB.conectar();
			Communicator communicator = Util.initialize(args, cfg)
		) {
			DAO dao = new DAO(conexion);
			QueryServer server = new QueryServer(dao);

			ObjectAdapter adapter = communicator.createObjectAdapter("QueryServer");
			adapter.add(server, Util.stringToIdentity("QueryServer"));
			adapter.activate();
			System.out.println("QueryServer up ...");


			communicator.waitForShutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConexionDB.cerrarPool(); // si usas pool
		}
	}
}

