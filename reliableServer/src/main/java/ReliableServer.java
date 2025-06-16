import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import BrokerIce.BrokerImpPrx;
import Query.QueryProxyIPrx;
import communication.Notification;
import reliableMessage.ACKServicePrx;
import reliableMessage.RMDestinationPrx;
import services.RMReciever;
import services.RMSender;
import threads.RMJob;

public class ReliableServer {

    private static Communicator communicator;
    private static QueryProxyIPrx queryProxy;

    public static void main(String[] args) {
        communicator = Util.initialize(args, "rmservice.config");

        try {
            // Conectarse al Broker
            BrokerImpPrx broker = BrokerImpPrx.checkedCast(
                communicator.stringToProxy("Broker:tcp -h localhost -p 10020")
            );
            

            if (broker == null) {
                throw new RuntimeException("No se pudo conectar al broker");
            }

            // Obtener proxy del QueryProxy desde el broker
            String proxyStr = broker.getServer();

            if (proxyStr.equals("NaN")) {
                throw new RuntimeException("No hay servidores disponibles en el broker");
            }

            queryProxy = QueryProxyIPrx.checkedCast(
                communicator.stringToProxy(proxyStr)
            );

            if (queryProxy == null) {
                throw new RuntimeException("El proxy obtenido no implementa la interfaz esperada");
            }

            System.out.println("ReliableServer conectado al QueryProxy correctamente");

            //RM
            Notification notification = new Notification();
            RMJob job = new RMJob(notification);
            RMReciever rec = new RMReciever(job);
            RMSender sender = new RMSender(job, notification);

            ObjectAdapter adapter = communicator.createObjectAdapter("RMService");
            adapter.add(sender, Util.stringToIdentity("Sender"));
            adapter.add(new ReliableQueryI(), com.zeroc.Ice.Util.stringToIdentity("ReliableQuery"));

            ObjectPrx prx = adapter.add(rec, Util.stringToIdentity("AckCallback"));
            notification.setAckService(ACKServicePrx.checkedCast(prx));

            // Configurar el proxy del servidor de destino
            RMDestinationPrx dest = RMDestinationPrx
                 .uncheckedCast(communicator.stringToProxy("Receiver:tcp -h localhost -p 10011"));
            sender.setServerProxy(dest, null);

            adapter.activate();
            job.start();

            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVotingTableByVoterId(String voterId) {
        System.out.println("Consultando mesa para ID: " + voterId);
        if (queryProxy == null) {
            System.err.println("El QueryProxy no está inicializado");
            return null;
        }
        return queryProxy.getVotingTableById(voterId);
    }
}
