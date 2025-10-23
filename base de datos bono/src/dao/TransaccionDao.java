

package dao;

import Conecion.DatabaseConnection;
import Entidades.Transaccion;
import Entidades.Tiquete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDao {

    private final TiqueteDao tiqueteDAO = new TiqueteDao();

  
    public boolean comprarTiquete(int clienteId, int tiqueteId) {
        String lockTiqueteSql =
                "SELECT t.id, t.vendido, t.propietario_id, t.localidad_id " +
                "FROM tiquete t WHERE t.id = ? FOR UPDATE";
        String precioSql =
                "SELECT l.precio FROM localidad l WHERE l.id = ?";
        String insertarTransaccionSql =
                "INSERT INTO transaccion (cliente_id, tiquete_id, monto, fecha_compra, es_reserva_organizador) " +
                "VALUES (?, ?, ?, ?, ?)";
        String actualizarTiqueteSql =
                "UPDATE tiquete SET vendido = 1, propietario_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            Integer localidadId = null;
            boolean vendido = true;
            Integer propietarioActual = null;

            try (PreparedStatement ps = conn.prepareStatement(lockTiqueteSql)) {
                ps.setInt(1, tiqueteId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("El tiquete no existe.");
                        conn.rollback();
                        return false;
                    }
                    vendido = rs.getBoolean("vendido");
                    propietarioActual = (Integer) rs.getObject("propietario_id");
                    localidadId = rs.getInt("localidad_id");
                }
            }

            if (vendido || propietarioActual != null) {
                System.out.println("el tiqete no esta disponible para la venta o no existe");
                conn.rollback();
                return false;
            }

            double precio = 0.0;
            try (PreparedStatement ps = conn.prepareStatement(precioSql)) {
                ps.setInt(1, localidadId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("no existe la localidad del tiquetre.");
                        conn.rollback();
                        return false;
                    }
                    precio = rs.getDouble("precio");
                }
            }

            Timestamp ahora = new Timestamp(System.currentTimeMillis());
            boolean esReservaOrganizador = false; 

            try (PreparedStatement ps = conn.prepareStatement(insertarTransaccionSql)) {
                ps.setInt(1, clienteId);
                ps.setInt(2, tiqueteId);
                ps.setDouble(3, precio);
                ps.setTimestamp(4, ahora);
                ps.setBoolean(5, esReservaOrganizador);
                ps.executeUpdate();
            }

         
            try (PreparedStatement ps = conn.prepareStatement(actualizarTiqueteSql)) {
                ps.setInt(1, clienteId);
                ps.setInt(2, tiqueteId);
                ps.executeUpdate();
            }

            conn.commit();
            System.out.println("Compra registrada.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }


    public int insertar(Transaccion t) {
        String sql = "INSERT INTO transaccion " +
                "(cliente_id, tiquete_id, monto, fecha_compra, es_reserva_organizador) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getClienteId());
            ps.setInt(2, t.getTiqueteId());
            ps.setDouble(3, t.getMonto());
            ps.setTimestamp(4, t.getFechaCompra());
            ps.setBoolean(5, t.isEsReservaOrganizador());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Transaccion> listarPorCliente(int clienteId) {
        List<Transaccion> out = new ArrayList<>();
        String sql = "SELECT id, cliente_id, tiquete_id, monto, fecha_compra, es_reserva_organizador " +
                     "FROM transaccion WHERE cliente_id = ? ORDER BY fecha_compra DESC, id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaccion t = new Transaccion();
                    t.setId(rs.getInt("id"));
                    t.setClienteId(rs.getInt("cliente_id"));
                    t.setTiqueteId(rs.getInt("tiquete_id"));
                    t.setMonto(rs.getDouble("monto"));
                    t.setFechaCompra(rs.getTimestamp("fecha_compra"));
                    t.setEsReservaOrganizador(rs.getBoolean("es_reserva_organizador"));
                    out.add(t);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public Transaccion obtenerPorId(int id) {
        String sql = "SELECT id, cliente_id, tiquete_id, monto, fecha_compra, es_reserva_organizador " +
                     "FROM transaccion WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaccion t = new Transaccion();
                    t.setId(rs.getInt("id"));
                    t.setClienteId(rs.getInt("cliente_id"));
                    t.setTiqueteId(rs.getInt("tiquete_id"));
                    t.setMonto(rs.getDouble("monto"));
                    t.setFechaCompra(rs.getTimestamp("fecha_compra"));
                    t.setEsReservaOrganizador(rs.getBoolean("es_reserva_organizador"));
                    return t;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Transaccion> listar() {
        List<Transaccion> out = new ArrayList<>();
        String sql = "SELECT id, cliente_id, tiquete_id, monto, fecha_compra, es_reserva_organizador FROM transaccion";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Transaccion t = new Transaccion();
                t.setId(rs.getInt("id"));
                t.setClienteId(rs.getInt("cliente_id"));
                t.setTiqueteId(rs.getInt("tiquete_id"));
                t.setMonto(rs.getDouble("monto"));
                t.setFechaCompra(rs.getTimestamp("fecha_compra"));
                t.setEsReservaOrganizador(rs.getBoolean("es_reserva_organizador"));
                out.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }
}

