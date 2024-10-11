package dbConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OdooJDBCConnection {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://192.168.21.61:5432/DaniBD"; // Asegúrate de que el puerto sea correcto
    private static final String USER = "odoo";
    private static final String PASSWORD = "abcd*1234";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conectar a la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos de Odoo.");

            // Consulta de ejemplo
            String sql = "SELECT login FROM res_users";  // Ajusta los nombres según sea necesario
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            // Mostrar resultados de la consulta
            while (resultSet.next()) {
                String name = resultSet.getString("login");
                System.out.println("Nombre del Partner: " + name);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, PreparedStatement y Connection
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
