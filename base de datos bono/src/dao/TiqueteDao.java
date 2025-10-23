package dao;

import Conecion.DatabaseConnection;
import Entidades.Tiquete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiqueteDao {
	
    public int insertar(Tiquete t) {
        String sql = "INSERT INTO tiquete (tipo, codigo, localidad_id, asiento_num, paquete_id, vendido, propietario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getCodigo());
            ps.setInt(3, t.getLocalidadId());
            if (t.getAsientoNum()!=null) ps.setInt(4, t.getAsientoNum()); else ps.setNull(4, Types.INTEGER);
            if (t.getPaqueteId()!=null) ps.setInt(5, t.getPaqueteId()); else ps.setNull(5, Types.INTEGER);
            ps.setBoolean(6, t.isVendido());
            if (t.getPropietarioId()!=null) ps.setInt(7, t.getPropietarioId()); else ps.setNull(7, Types.INTEGER);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) { int id = rs.getInt(1); t.setId(id); return id; }
        } catch (SQLException e) {
            System.out.println(" no se pudo insertar tiquetes " + e.getMessage());
            
        }
        return -1;
    }

    
    public int insertarDesdeLocalidad(Tiquete t, Connection conn) throws SQLException {
        String sql = "INSERT INTO tiquete (tipo, codigo, localidad_id, asiento_num, paquete_id, vendido, propietario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getCodigo());
            ps.setInt(3, t.getLocalidadId());
            if (t.getAsientoNum()!=null) ps.setInt(4, t.getAsientoNum()); else ps.setNull(4, Types.INTEGER);
            if (t.getPaqueteId()!=null) ps.setInt(5, t.getPaqueteId()); else ps.setNull(5, Types.INTEGER);
            ps.setBoolean(6, t.isVendido());
            if (t.getPropietarioId()!=null) ps.setInt(7, t.getPropietarioId()); else ps.setNull(7, Types.INTEGER);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) { int id = rs.getInt(1); t.setId(id); return id; }
        }
        return -1;
    }

    public Tiquete obtenerPorId(int id) {
        String sql = "SELECT * FROM tiquete WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Tiquete t = new Tiquete();
                t.setId(rs.getInt("id"));
                t.setTipo(rs.getString("tipo"));
                t.setCodigo(rs.getString("codigo"));
                t.setLocalidadId(rs.getInt("localidad_id"));
                int asiento = rs.getInt("asiento_num");
                if (!rs.wasNull()) t.setAsientoNum(asiento);
                int paquete = rs.getInt("paquete_id");
                if (!rs.wasNull()) t.setPaqueteId(paquete);
                t.setVendido(rs.getBoolean("vendido"));
                int prop = rs.getInt("propietario_id");
                if (!rs.wasNull()) t.setPropietarioId(prop);
                return t;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Tiquete> listarPorLocalidad(int locId) {
        List<Tiquete> out = new ArrayList<>();
        String sql = "SELECT * FROM tiquete WHERE localidad_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tiquete t = new Tiquete();
                t.setId(rs.getInt("id"));
                t.setTipo(rs.getString("tipo"));
                t.setCodigo(rs.getString("codigo"));
                t.setLocalidadId(rs.getInt("localidad_id"));
                int asiento = rs.getInt("asiento_num");
                if (!rs.wasNull()) t.setAsientoNum(asiento);
                t.setVendido(rs.getBoolean("vendido"));
                int prop = rs.getInt("propietario_id");
                if (!rs.wasNull()) t.setPropietarioId(prop);
                out.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    
    public boolean actualizarPropietario(int tiqueteId, Integer nuevoPropietario, Connection conn) throws SQLException {
        String sql = "UPDATE tiquete SET propietario_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (nuevoPropietario != null) ps.setInt(1, nuevoPropietario); else ps.setNull(1, Types.INTEGER);
            ps.setInt(2, tiqueteId);
            return ps.executeUpdate() > 0;
        }
    }
}
