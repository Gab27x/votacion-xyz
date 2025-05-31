import controller.VotationController; // Importa tu VotationController

// Estos imports de Ice se mantienen porque ya están en tu try-with-resources
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

public class VotingTable {

    public static void main(String[] args) {
        
        // El bloque try-with-resources para Communicator se mantiene como lo tienes
        try (Communicator communicator = Util.initialize(args, "VotingTable.cfg")) {

            System.out.println("Voting Table is starting...");

            // sendVotePrx = // Esto no se usará por ahora, lo dejaremos comentado o eliminaremos si quieres

            // === AÑADIR ESTAS LÍNEAS PARA INICIAR EL CONTROLADOR Y LA CLI ===
            VotationController votationController = new VotationController();
            votationController.run(); // Esto ejecutará la lógica de la CLI
            // =============================================================

            // communicator.waitForShutdown(); // Esto esperaría conexiones Ice, que no estamos probando ahora
                                             // Puedes dejarlo comentado si quieres que el programa termine
                                             // después de la interacción CLI, o quitarlo.
                                             // Si lo dejas, el programa no terminará hasta que se apague Ice externamente.
                                             // Para pruebas de CLI, es mejor que termine.
            
            System.out.println("Voting Table finished CLI interaction. Shutting down.");
        } catch (Exception e) {
            System.err.println("Error in Voting Table main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}