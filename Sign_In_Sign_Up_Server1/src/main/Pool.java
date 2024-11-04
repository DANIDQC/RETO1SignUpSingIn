package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Pool {
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final int poolSize;

    // Constructor que inicializa el pool leyendo las propiedades de configuración
    public Pool() {
        Properties properties = loadProperties();
        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.poolSize = Integer.parseInt(properties.getProperty("db.pool.size", "10"));
        this.availableConnections = new ArrayList<>(poolSize);

        // Crear el pool inicial de conexiones
        for (int i = 0; i < poolSize; i++) {
            availableConnections.add(createConnection());
        }
    }

    // Cargar propiedades desde el archivo database.properties
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el archivo de propiedades", e);
        }
        return properties;
    }

    // Crea una nueva conexión
    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear una conexión", e);
        }
    }

    // Método para obtener una conexión del pool
    public Connection getConnection() {
        if (availableConnections.isEmpty()) {
            throw new RuntimeException("No hay conexiones disponibles");
        }

        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    // Método para devolver una conexión al pool
    public void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }

    // Método para cerrar todas las conexiones
    public void closeAllConnections() {
        for (Connection connection : availableConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Tamaño del pool
    public int getSize() {
        return availableConnections.size() + usedConnections.size();
    }
}
