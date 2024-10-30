/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class Pool {
    private final Queue<Connection> availableConnections;
    private final int MAX_CONNECTIONS;
    // Parámetros de conexión a la base de datos
    private final String jdbcUrl = "jdbc:postgresql://192.168.21.92:5432/DaniBD"; 
    private final String usernameDB = "odoo";
    private final String passwordDB = "abcd*1234";

    public Pool(int maxConnections) throws SQLException {
        this.availableConnections = new LinkedList<>();
        this.MAX_CONNECTIONS = maxConnections;

        // Inicializar el pool de conexiones con conexiones JDBC
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            availableConnections.offer(createConnection());
        }
    }

    // Método para crear una nueva conexión JDBC
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://192.168.21.92:5432/GuilleDB", "odoo", "abcd*1234");
    }

    // Método sincronizado para obtener una conexión del pool
    public synchronized Connection getConnection() throws SQLException, InterruptedException {
        while (availableConnections.isEmpty()) {
            System.out.println("No hay conexiones disponibles. Esperando...");
            wait();  // Esperamos hasta que una conexión esté disponible
        }
        System.out.println("Obteniendo conexión del pool...");
        return availableConnections.poll();  // Obtenemos una conexión del pool
    }

    // Método sincronizado para liberar una conexión y devolverla al pool
    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            availableConnections.offer(connection);  // Devolver la conexión al pool
            System.out.println("Conexión liberada y añadida al pool.");
            notifyAll();  // Notificamos a los hilos que están esperando una conexión
        }
    }

    // Método para cerrar todas las conexiones del pool cuando ya no son necesarias
    public synchronized void closeAllConnections() throws SQLException {
        while (!availableConnections.isEmpty()) {
            Connection connection = availableConnections.poll();
            if (connection != null) {
                connection.close();
            }
        }
    }
}
