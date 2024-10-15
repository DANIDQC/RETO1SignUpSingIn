package dbConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OdooJDBCConnection {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://192.168.21.144:5432/DaniBD";
    private static final String USER = "odoo";
    private static final String PASSWORD = "abcd*1234";

    private Connection connection;

    // Método para abrir la conexión
    public void conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos de Odoo.");
        }
    }

    // Método para ejecutar una consulta
    public void mostrarUsuarios() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT login FROM res_users";  // Ajusta según sea necesario
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("login");
                System.out.println("Nombre del Usuario: " + name);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
    }

    // Método para cerrar la conexión
    public void desconectar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
