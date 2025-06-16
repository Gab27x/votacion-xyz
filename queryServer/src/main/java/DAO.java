import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    private Connection conexion;

    public DAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para ejecutar cualquier SELECT genérico
    public void ejecutarConsultaGenerica(String query, String[] columnas) {
        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                StringBuilder resultado = new StringBuilder();
                for (int i = 0; i < columnas.length; i++) {
                    resultado.append(rs.getString(columnas[i]));
                    if (i < columnas.length - 1)
                        resultado.append(", ");
                }
                System.out.println(resultado);
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar consulta.");
            e.printStackTrace();
        }
    }

    // Método para ejecutar INSERT, UPDATE, DELETE
    public int ejecutarActualizacionGenerica(String query) {
        try (Statement stmt = conexion.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar actualización.");
            e.printStackTrace();
            return 0;
        }
    }

    // Método específico para obtener info completa de un ciudadano por su ID
    public String obtenerInfoCiudadanoPorId(int ciudadanoId) {
        String resultado = "";

        String query = 
                "SELECT d.nombre AS nombre_departamento, " +
                "m.nombre AS nombre_municipio, " +
                "p.nombre AS nombre_puesto, " +
                "c.mesa_id, " +
                "d.id AS id_departamento, " +
                "m.id AS id_municipio, " +
                "p.id AS id_puesto " +
                "FROM ciudadano c " +
                "JOIN mesa_votacion mv ON c.mesa_id = mv.id " +
                "JOIN puesto_votacion p ON mv.puesto_id = p.id " +
                "JOIN municipio m ON p.municipio_id = m.id " +
                "JOIN departamento d ON m.departamento_id = d.id " +
                "WHERE c.id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, ciudadanoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultado = rs.getString("nombre_departamento") + ", " +
                        rs.getString("nombre_municipio") + ", " +
                        rs.getString("nombre_puesto") + ", " +
                        rs.getInt("mesa_id") + ", " +
                        rs.getInt("id_departamento") + ", " +
                        rs.getInt("id_municipio") + ", " +
                        rs.getInt("id_puesto");
            } else {
                resultado = "No se encontró ciudadano con ese ID.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado = "Error en la consulta: " + e.getMessage();
        }

        return resultado;
    }
}
