package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionDB {

    private static HikariDataSource dataSource;

    static {
        try {
            System.out.println(" Inicializando el pool de conexiones...");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://192.168.131.101:5430/votaciones_marvannatgab");
            config.setUsername("grupomvng");
            config.setPassword("12345");

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setLeakDetectionThreshold(60000);
            config.setInitializationFailTimeout(-1); // no fallar en arranque

            dataSource = new HikariDataSource(config);
            System.out.println(" Pool inicializado correctamente");

        } catch (Throwable e) {
            System.err.println("Error al inicializar ConexionDB:");
            e.printStackTrace();
            throw new ExceptionInInitializerError("No se pudo inicializar ConexionDB");
        }
    }

    public static Connection conectar() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("El pool de conexiones no está inicializado.");
        }
        return dataSource.getConnection();
    }

    public static void cerrarPool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool cerrado");
        }
    }
}

