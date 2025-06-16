package utils;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

import model.Vote;

public class DAO {
    private final Connection conn;

    public DAO(Connection conn) {
        this.conn = conn;
    }

    public int insertarVoto(Vote vote) throws SQLException {
        String sql = "INSERT INTO voto (candidato_id) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, Integer.parseInt(vote.vote));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del voto insertado.");
                }
            }
        }
    }
}
