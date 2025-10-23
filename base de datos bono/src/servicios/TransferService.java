package servicios;
import Conecion.DatabaseConnection;
import dao.TiqueteDao;
import Entidades.Tiquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class TransferService {
	private final TiqueteDao tiqueteDAO = new TiqueteDao();

    public boolean transferirTiquete(int tiqueteId, int fromUsuarioId, int toUsuarioId) {
        Tiquete t = tiqueteDAO.obtenerPorId(tiqueteId);
        if (t == null) {
            System.out.println("el tiquete no fue encontrado");
            return false;
        }
        if (!t.isVendido() || t.getPropietarioId() == null || t.getPropietarioId() != fromUsuarioId) {
            System.out.println(" el usuario no es el propietario del tiquete.");
            return false;
        }
        if ("Deluxe".equalsIgnoreCase(t.getTipo())) {
            System.out.println("recuerda que los paquetes deluxe no se pueden rtransferir");
            return false;
        }
        if ("Multiple".equalsIgnoreCase(t.getTipo())) {
            Integer paquete = t.getPaqueteId();
            if (paquete == null) {
                System.out.println("el tiquete multiple sin paquete definido, no se puede transferir.");
                return false;
            }
            return transferirPaquete(paquete, fromUsuarioId, toUsuarioId);
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            boolean ok = tiqueteDAO.actualizarPropietario(tiqueteId, toUsuarioId, conn);
            if (!ok) { conn.rollback(); System.out.println(" No se pudo transferir."); return false; }
            conn.commit();
            System.out.println("la trasnferencia fue realizada.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean transferirPaquete(int paqueteId, int fromUsuarioId, int toUsuarioId) {
        String sql = "SELECT id, propietario_id FROM tiquete WHERE paquete_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paqueteId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Integer> ids = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int prop = rs.getInt("propietario_id");
                if (prop != fromUsuarioId) {
                    System.out.println("el paquete no es del propietario, por lo que no se puede transferir en su totalidad");
                    return false;
                }
                ids.add(id);
            }
            conn.setAutoCommit(false);
            for (Integer id : ids) {
                boolean ok = tiqueteDAO.actualizarPropietario(id, toUsuarioId, conn);
                if (!ok) { conn.rollback(); System.out.println("no se pudo transferir el paquete"); return false; }
            }
            conn.commit();
            System.out.println("el paqute fue transferido correctamente");
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
