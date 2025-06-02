package communication;

import messageReliable.ACKService;
import com.zeroc.Ice.Current;

public class AckServiceImpl implements ACKService {
    @Override
    public void ack(String voteId, Current current) {
        System.out.println("ACK recibido del server para el voto: " + voteId);
    }
}