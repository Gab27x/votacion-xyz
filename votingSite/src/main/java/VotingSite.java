import com.zeroc.Ice.*;

public class VotingSite implements Demo.VotingSite {  // Implementa la interfaz generada

    @Override
    public void send(int candidateId, Current current) {
        System.out.println("Recibido voto para candidato: " + candidateId);
        // Aquí va tu lógica para procesar el voto
    }

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "VotingSite.cfg")) {

            System.out.println("Voting Site is starting...");

            ObjectAdapter adapter = communicator.createObjectAdapter("VotingSite");

			VotingSite servant = new VotingSite();
			adapter.add(servant, Util.stringToIdentity("VotingSite"));

			adapter.activate();


            System.out.println("Voting Site listo y esperando solicitudes...");

            communicator.waitForShutdown();

		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
    }
}