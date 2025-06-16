import java.sql.Connection;
import com.zeroc.Ice.Current;


import model.ReliableMessage;
import model.Vote;
import reliableMessage.ACKServicePrx;
import reliableMessage.RMDestination;
import utils.ConexionDB;
import utils.DAO;

public class ServiceImp implements RMDestination {

    @Override
    public void reciveMessage(ReliableMessage rmessage, ACKServicePrx prx, Current current) {
        try (Connection conn = ConexionDB.conectar()) {
            DAO dao = new DAO(conn);

            Vote voto = rmessage.getVote();
            int idGenerado = dao.insertarVoto(voto);

            System.out.println("Voto recibido y guardado. ID generado: " + idGenerado);
            System.out.println("UUID del mensaje: " + rmessage.getUuid());

            // Enviar ACK al origen
            prx.ack(rmessage.getUuid());

        } catch (Exception e) {
            System.err.println("Error al guardar el voto:");
            e.printStackTrace();
        }
    }
}
