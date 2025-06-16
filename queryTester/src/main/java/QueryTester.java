import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import Query.QueryDeviceIPrx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QueryTester {

    public static void main(String[] args) {
        String cfg = args.length > 0 ? args[0] : "QueryTester.cfg";
        String csvPath = "ciudadanos.csv";

        try (Communicator communicator = Util.initialize(args, cfg)) {

            QueryDeviceIPrx queryDeviceIPrx = QueryDeviceIPrx.checkedCast(
                    communicator.stringToProxy(communicator.getProperties().getProperty("QueryDevice.Proxy")));

            if (queryDeviceIPrx == null) {
                throw new RuntimeException("No se pudo obtener el proxy QueryDevice.");
            }

            List<Long> tiemposMs = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    String documento = partes[0];
                    String mesaId = partes[1];

                    long inicioMs = System.currentTimeMillis();
                    String resultado = queryDeviceIPrx.query(documento);
                    long finMs = System.currentTimeMillis();

                    long duracion = finMs - inicioMs;
                    tiemposMs.add(duracion);


                    System.out.printf("Documento: %s | Tiempo: %d ms | Mesa: %s | Target: %s%n",
                            documento, duracion, mesaId, resultado);
                }
            }

            long total = tiemposMs.stream().mapToLong(Long::longValue).sum();
            double promedio = tiemposMs.isEmpty() ? 0 : (double) total / tiemposMs.size();

            System.out.printf("Tiempo promedio por consulta: %.2f ms%n", promedio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

