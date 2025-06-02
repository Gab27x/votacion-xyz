import com.zeroc.Ice.Current;
import model.ReliableMessage;
import model.Vote;
import messageReliable.ACKServicePrx;
import messageReliable.RMDestination;

public class ServiceImp implements RMDestination {

    @Override
    public void reciveMessage(Vote vote, ACKServicePrx prx, Current current) {
        // Lógica de recepción del voto
        System.out.println("Voto recibido desde VotingSite:");

        prx.ack(vote.getCandidate());
    }
}

