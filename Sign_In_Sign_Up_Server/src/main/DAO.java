package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO implements Signable {

    private final Pool connectionPool;
    private Connection con;
    private PreparedStatement stmt;
    private ResourceBundle fichConf;
    private ResultSet rs;

    public DAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User signIn(User usuario) {
        Connection connection = null;
        try {
            // Obtener la conexión del pool
            connection = connectionPool.getConnection();
            
            // Preparar la consulta SQL
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getPass());

            ResultSet rs = stmt.executeQuery();

            // Si se encuentra un resultado, el usuario existe y el login es correcto
            if (rs.next()) {
                System.out.println("Inicio de sesión exitoso para: " + usuario.getLogin());
                return usuario; // O devolver un nuevo User con información adicional si lo prefieres
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
                return null; // Devuelve null si no se encuentra el usuario
            }
        } catch (SQLException | InterruptedException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Liberar la conexión
            if (connection != null) {
                try {
                    connectionPool.releaseConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // Método que ejecuta una consulta SELECT
    public void executeSelectQuery(String query) {
        Connection connection = null;
        try {
            // Obtener una conexión del pool
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Procesar los resultados de la consulta
            while (resultSet.next()) {
                System.out.println("Resultado: " + resultSet.getString(1));
            }

            statement.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Devolver la conexión al pool
            if (connection != null) {
                try {
                    connectionPool.releaseConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User signUp(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
