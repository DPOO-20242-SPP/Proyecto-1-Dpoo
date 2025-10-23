package Conecion;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
	public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("🎉 ¡Conexión exitosa!");
            } else {
                System.out.println("❌ No se logró conectar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
