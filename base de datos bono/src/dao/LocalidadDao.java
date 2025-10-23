
package dao;

import Conecion.DatabaseConnection;
import Entidades.Localidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalidadDao {
    

    public int insertar(Localidad l, boolean numerada) {
        if (l.getCapacidad() <= 0 || l.getPrecio() <= 0) {
            System.out.println("COpacidad y precio deben ser > mayores a 0");
            return -1;
        }
        String sql = "INSERT INTO localidad (nombre, capacidad, precio, evento_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, l.getNombre());
            ps.setInt(2, l.getCapacidad());
            ps.setDouble(3, l.getPrecio());
            ps.setInt(4, l.getEventoId()); 

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Localidad> listarPorEvento(int eventoId) {
        List<Localidad> out = new ArrayList<>();
        String sql = "SELECT * FROM localidad WHERE evento_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Localidad loc = new Localidad();
                loc.setId(rs.getInt("id"));
                loc.setNombre(rs.getString("nombre"));
                loc.setCapacidad(rs.getInt("capacidad"));
                loc.setPrecio(rs.getDouble("precio"));
                loc.setEventoId(rs.getInt("evento_id")); 
                out.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }


    public Localidad obtenerPorId(int id) {
        String sql = "SELECT * FROM localidad WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Localidad loc = new Localidad();
                loc.setId(rs.getInt("id"));
                loc.setNombre(rs.getString("nombre"));
                loc.setCapacidad(rs.getInt("capacidad"));
                loc.setPrecio(rs.getDouble("precio"));
                loc.setEventoId(rs.getInt("evento_id"));
                return loc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Localidad> listar() {
        List<Localidad> out = new ArrayList<>();
        String sql = "SELECT * FROM localidad";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Localidad loc = new Localidad();
                loc.setId(rs.getInt("id"));
                loc.setNombre(rs.getString("nombre"));
                loc.setCapacidad(rs.getInt("capacidad"));
                loc.setPrecio(rs.getDouble("precio"));
                loc.setEventoId(rs.getInt("evento_id"));
                out.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}

