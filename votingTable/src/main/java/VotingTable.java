import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import controller.VotationController;
import model.Vote;
import Demo.VotingSitePrx;

public class VotingTable implements Demo.VotingTable {

    private static VotationController controller;

    @Override
    public void vote(String document, int candidateId, Current current) {
        Vote newVote = new Vote(document, candidateId);
        controller.sendVote(newVote);
    }

    public static void main(String[] args) {
        System.out.println("VotingTable is starting up...");

        try (Communicator communicator = Util.initialize(args, "VotingTable.cfg")) {

            VotingSitePrx sitePrx = VotingSitePrx.checkedCast(
                communicator.propertyToProxy("VotingSite.Proxy")
            );

            if (sitePrx == null) {
                throw new RuntimeException("Failed to obtain proxy to VotingSite.");
            }

            controller = new VotationController(sitePrx);

            VotingTable servant = new VotingTable();

            ObjectAdapter adapter = communicator.createObjectAdapter("VotingTable");
            adapter.add(servant, Util.stringToIdentity("VotingTable"));
            adapter.activate();

            System.out.println("VotingTable is up and running.");

            controller.run();

            communicator.waitForShutdown();

            System.out.println("VotingTable is shutting down.");

        } catch (Exception e) {
            System.err.println("VotingTable encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
