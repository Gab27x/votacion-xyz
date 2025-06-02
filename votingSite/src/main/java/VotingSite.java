import com.zeroc.Ice.*;
import communication.Notification;
import controller.VotingSiteController;
import messaging.ReliableMessenger;
import services.RMSender;
import communication.AckServiceImpl;
import messageReliable.RMDestinationPrx;
import model.Vote;
import messaging.RMJob;

public class VotingSite implements Demo.VotingSite {

    private VotingSiteController controller;

    public VotingSite(VotingSiteController controller) {
        this.controller = controller;
    }

    @Override
    public void send(int candidateId, Current current) {
        Vote vote = new Vote("votante-generico", String.valueOf(candidateId));
        controller.receiveVote(vote);
    }

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "VotingSite.cfg")) {
            System.out.println("Voting Site is starting...");

            // Adapters
            ObjectAdapter adapter = communicator.createObjectAdapter("VotingSite");
            ObjectAdapter ackAdapter = communicator.createObjectAdapterWithEndpoints("AckAdapter", "tcp -h localhost -p 10013");

            // Notification y ACK activación
            Notification notification = new Notification(ackAdapter);
            ackAdapter.activate();

            // Obtener proxy del Server (antes de crear RMSender)
            RMDestinationPrx serverPrx = RMDestinationPrx.checkedCast(
                communicator.stringToProxy("Service:tcp -h localhost -p 10012")
            );

            RMJob job = new RMJob(notification); 
            RMSender rmSender = new RMSender(job, notification);
            rmSender.setServer(serverPrx); 

            ReliableMessenger messenger = new ReliableMessenger(rmSender);
            messenger.start();

            VotingSiteController controller = new VotingSiteController(messenger);
            VotingSite servant = new VotingSite(controller);
            adapter.add(servant, Util.stringToIdentity("VotingSite"));
            adapter.activate();

            System.out.println("Voting Site listo y esperando solicitudes...");

            communicator.waitForShutdown();

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
