package Conecion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/evento_venue?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      
    private static final String PASSWORD = "1234"; 

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión establecida con  la base de datosMySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println(" No se encontró el driver ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("no se pudo conectar a MySQL.");
            e.printStackTrace();
        }
        return conn;
    }
}
