import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import Reliable.ReliableQueryPrx;
import controller.VotationController;
import controller.VotingTableI;
import model.TableVote;
import reliableMessage.RMSourcePrx;

public class VotingTable implements Demo.VotingTable {

    private static VotationController controller;

    @Override
    public int vote(String document, int candidateId, Current current) {
        TableVote newVote = new TableVote(document, candidateId);
        controller.sendVote(newVote);
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("VotingTable is starting up...");
        String cfg = args.length > 0 ? args[0] : "QueryDevice.cfg";

        try (Communicator communicator = Util.initialize(args, cfg)) {

            RMSourcePrx rm = RMSourcePrx.checkedCast(
                    communicator.propertyToProxy("Sender.Proxy"));

            if (rm == null) {
                throw new RuntimeException("Failed to obtain proxy to Sender.");
            }

            ReliableQueryPrx reliableQuery = ReliableQueryPrx.checkedCast(
                    communicator.propertyToProxy("Sender.Proxy"));

            VotationController controller = new VotationController(rm, reliableQuery, communicator);

            ObjectAdapter adapter = communicator.createObjectAdapter("VotingTable");

            Demo.VotingTable votingTableServant = new VotingTableI(controller);
            adapter.add(votingTableServant, Util.stringToIdentity("VotingTable"));
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
