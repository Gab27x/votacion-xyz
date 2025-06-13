import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server is starting up...");

        try (Communicator communicator = Util.initialize(args, "server.cfg")) {
            ServiceImp imp = new ServiceImp();

            ObjectAdapter adapter = communicator.createObjectAdapter("Server");
            adapter.add(imp, Util.stringToIdentity("Service"));
            adapter.activate();

            System.out.println("Server is up and running.");

            communicator.waitForShutdown();

            System.out.println("Server is shutting down.");

        } catch (Exception e) {
            System.err.println("Server encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

