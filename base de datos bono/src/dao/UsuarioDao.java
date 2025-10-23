package dao;

import Conecion.DatabaseConnection;
import Entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, correo, password, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTipo());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("se inserto el usuario 10 de 10");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("no se logro insertar el usuario.");
            e.printStackTrace();
        }
        return false;
    }


    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getString("tipo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("No se pudo listar usuarios.");
            e.printStackTrace();
        }
        return lista;
    }

   
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getString("tipo"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("NO se logro ENCONTRAR EL USUARIO POR ID.");
            e.printStackTrace();
        }
        return null;
    }

  
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setTipo(rs.getString("tipo"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("NNO se pudo buscar el usuario por ID.");
            e.printStackTrace();
        }
        return null;
    }


    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, correo=?, password=?, tipo=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTipo());
            ps.setInt(5, u.getId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Se  actualizado el usuario.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("No se pudo actualizar el usuariop.");
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Se elimino el usuario.");
                return true;
            } else {
                System.out.println(" No existe el usario con el ID" + id);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" no se puede eliminar el usuario porque tiene registros asociados.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("nO se pudo eliminar el usuario.");
            e.printStackTrace();
        }

        return false;
    }
    
}