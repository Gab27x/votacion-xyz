import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {

    public static void main(String[] args) {
        Communicator com = Util.initialize();

        ServiceImp imp = new ServiceImp(); // Implementación de RMDestination

        ObjectAdapter adapter = com.createObjectAdapterWithEndpoints("Server", "tcp -h localhost -p 10012");
        adapter.add(imp, Util.stringToIdentity("Service"));

        adapter.activate();
        System.out.println("[Server] Esperando mensajes...");
        com.waitForShutdown();
    }
}

