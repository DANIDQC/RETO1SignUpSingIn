package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;


public class Worker implements Runnable {
    private Socket clientSocket;
    private Pool connectionPool;
    private Signable sign;

    public Worker(Socket clientSocket, Pool connectionPool, Signable sign) {
        this.clientSocket = clientSocket;
        this.connectionPool = connectionPool;
        this.sign = sign;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message = objectInputStream.readUTF();
            Object object = objectInputStream.readObject();

            if (object instanceof User) {
                User user = (User) object;
                boolean result = false;

                // Obtener una conexión del pool de conexiones
                try (Connection connection = connectionPool.getConnection()) {
                    if (message.equalsIgnoreCase("signIn")) {
                        user = sign.signIn(user, connection); // El DAO utiliza la conexión
                    } else if (message.equalsIgnoreCase("signUp")) {
                        user = sign.signUp(user, connection); // El DAO utiliza la conexión
                    } else {
                        writer.println("Error: Acción no válida.");
                        return;
                    }

                    if (result) {
                        writer.println("OK: Acción '" + message + "' realizada con éxito para el usuario: " + user.getLogin());
                    } else {
                        writer.println("Error: No se pudo completar la acción '" + message + "' para el usuario: " + user.getLogin());
                    }

                } catch (Exception e) {
                    writer.println("Error: Problema con la base de datos.");
                    e.printStackTrace();
                } 

            } else {
                writer.println("Error: Objeto recibido no es de tipo User.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
