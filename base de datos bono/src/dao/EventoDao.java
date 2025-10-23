package dao;
import Conecion.DatabaseConnection;
import Entidades.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EventoDao {
	public boolean existeEventoEnVenueEnFecha(int venueId, Date fecha) {
        String sql = "SELECT COUNT(*) FROM evento WHERE venue_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venueId);
            ps.setDate(2, fecha);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int insertar(Evento e) {
        if (e.getNombre() == null || e.getNombre().isEmpty()) {
            System.out.println(" no tiene nombre el evento, es full necesario.");
            return -1;
        }
        if (e.getFecha()==null) {
            System.out.println(" no tiene fecha el evento, es full necesario.");
            return -1;
        }
     
        if (existeEventoEnVenueEnFecha(e.getVenueId(), e.getFecha())) {
            System.out.println(" el venue ya tiene un evento en esa fecha.");
            return -1;
        }
        String sql = "INSERT INTO evento (nombre, descripcion, fecha, organizador_id, venue_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            ps.setDate(3, e.getFecha());
            ps.setInt(4, e.getOrganizadorId());
            ps.setInt(5, e.getVenueId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                e.setId(id);
                System.out.println(" se creo PEFECT el  Evento con ID: " + id);
                return id;
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return -1;
    }

    public Evento obtenerPorId(int id) {
        String sql = "SELECT * FROM evento WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setFecha(rs.getDate("fecha"));
                e.setOrganizadorId(rs.getInt("organizador_id"));
                e.setVenueId(rs.getInt("venue_id"));
                return e;
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    public List<Evento> listar() {
        List<Evento> out = new ArrayList<>();
        String sql = "SELECT * FROM evento";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setFecha(rs.getDate("fecha"));
                e.setOrganizadorId(rs.getInt("organizador_id"));
                e.setVenueId(rs.getInt("venue_id"));
                out.add(e);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }
}
