package dbConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class DAO {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://192.168.21.144:5432/DaniBD";
    private static final String USER = "odoo";
    private static final String PASSWORD = "abcd*1234";

    private Connection connection;

    // Método para abrir la conexión
    public void conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        }
    }

    // Método para cerrar la conexión
    public void desconectar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }

    // Método para registrar un partner (usuario)
    public void RegistrarPartner(User usuario) throws SQLException {
        String sql = "INSERT INTO public.res_partner(id, company_id, name, email, active) "
                   + "VALUES (100, 1, ?, ?, true);";  // Suponiendo que 'id' es autoincremental

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getUsuario());  // Suponiendo que 'usuario' es el email

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar partner: " + e.getMessage());
            throw e;  // Re-lanzamos la excepción para manejo externo
        }
    }

    // Método para registrar un usuario
    public void RegistrarUsuario(User usuario) throws SQLException {
        String sql = "INSERT INTO public.res_users(id, company_id, partner_id, active, login, password) "
                   + "VALUES (100, 1, 100, true, ?, ?);";  // Suponiendo que el 'partner_id' es 100

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsuario());  
            preparedStatement.setString(2, usuario.getContraseina());  // Asegúrate de que esta variable existe en User

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            throw e;  // Re-lanzamos la excepción para manejo externo
        }
    }

    // Método para mostrar los usuarios en consola
    public void mostrarUsuarios() throws SQLException {
        String sql = "SELECT login FROM res_users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("login");
                System.out.println("Nombre del Usuario: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar usuarios: " + e.getMessage());
            throw e;  // Re-lanzamos la excepción para manejo externo
        }
    }

    // Método para buscar un nombre de usuario según el username
    public String buscarNombreporUsername(String username) throws SQLException {
        String sql = "SELECT rp.name "
                   + "FROM res_partner rp "
                   + "JOIN res_users ru ON rp.id = ru.partner_id "
                   + "WHERE ru.login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por username: " + e.getMessage());
            throw e;  // Re-lanzamos la excepción para manejo externo
        }

        return null;  // Retorna null si no se encontró el usuario
    }
}
