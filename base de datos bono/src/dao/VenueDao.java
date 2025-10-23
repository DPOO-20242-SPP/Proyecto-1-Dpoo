package dao;

import Conecion.DatabaseConnection;
import Entidades.Venue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDao {
	public int insertar(Venue v) {
        String sql = "INSERT INTO venue (nombre, direccion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getDireccion());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                v.setId(id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Venue obtenerPorId(int id) {
        String sql = "SELECT * FROM venue WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Venue v = new Venue();
                v.setId(rs.getInt("id"));
                v.setNombre(rs.getString("nombre"));
                v.setDireccion(rs.getString("direccion"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Venue> listar() {
        List<Venue> out = new ArrayList<>();
        String sql = "SELECT * FROM venue";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venue v = new Venue();
                v.setId(rs.getInt("id"));
                v.setNombre(rs.getString("nombre"));
                v.setDireccion(rs.getString("direccion"));
                out.add(v);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }
}
