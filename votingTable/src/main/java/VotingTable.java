import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import controller.VotationController;
import Demo.VotingSitePrx;

public class VotingTable {

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "VotingTable.cfg")) {

            System.out.println("Voting Table is starting...");

            // Obtener proxy remoto de VotingSite
            VotingSitePrx sitePrx = VotingSitePrx.checkedCast(
                communicator.propertyToProxy("VotingSite.Proxy")
            );

            if (sitePrx == null) {
                throw new RuntimeException("No se pudo obtener el proxy a VotingSite.");
            }

            // Inicializar controlador con proxy remoto
            VotationController controller = new VotationController(sitePrx);
            controller.run();

            System.out.println("Voting Table finished CLI interaction. Shutting down.");

        } catch (Exception e) {
            System.err.println("Error en VotingTable: " + e.getMessage());
        }
    }
}
