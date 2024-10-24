/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Application {

    private static final int PUERTO = 50100;
    public static void main(String[] args) throws SQLException {
        Pool connectionPool = new Pool(3);
        Signable sign = null;
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto " + PUERTO);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                         PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        String message = objectInputStream.readUTF();  
                        Object object = objectInputStream.readObject();
                        if (object instanceof User) {
                            User user = (User) object;
                            boolean result = false;
                            if (message.equalsIgnoreCase("signIn")) {
                                user = sign.signIn(user); 
                            } else if (message.equalsIgnoreCase("signUp")) {
                                user = sign.signUp(user); 
                            } else {
                                writer.println("Error: Acción no válida.");
                                return;
                            }

                            if (result) {
                                writer.println("OK: Acción '" + message + "' realizada con éxito para el usuario: " + user.getLogin());
                            } else {
                                writer.println("Error: No se pudo completar la acción '" + message + "' para el usuario: " + user.getLogin());
                            }
                        } else {
                            writer.println("Error: Objeto recibido no es de tipo User.");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeAllConnections();
        }
    }
}