/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ResourceBundle;
import java.util.Scanner;
import libreria.Request;
import libreria.Signable;
import libreria.Stream;
import libreria.User;
import vistas.FXMLSignInController;
import vistas.FXMLSignUpController;

/**
 *
 * @author Guillermo Flecha
 */
public class Cliente implements Signable {

    private static ServerSocket serverSocket;

    Socket socket = null;
    ObjectInputStream entrada = null;
    ObjectOutputStream salida = null;

    String IP = ResourceBundle.getBundle("modelo.conexionInfo").getString("IP");
    int PUERTO = Integer.parseInt(ResourceBundle.getBundle("modelo.conexionInfo").getString("PUERTO"));

    @Override
    public Stream signUp(User user) throws Exception {
        Stream stream = null;
        try {
            socket = new Socket(IP, PUERTO);
            System.out.println("Esperando que el servidor envíe algo....");
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            stream = new Stream(user, Request.SIGN_UP_SOLICITUD);
            salida.writeObject(stream);

            stream = (Stream) entrada.readObject();
            String message = stream.getMensaje().toString();
            FXMLSignUpController.actualizarInterfazConMensaje(message);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Fin cliente");
            return stream;
        }
    }

    @Override
    public Stream signIn(User user) throws Exception {
        Stream stream = null;

        try {
            socket = new Socket(IP, PUERTO);
            System.out.println("Esperando que el servidor envíe algo....");
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            stream = new Stream(user, Request.SIGN_IN_SOLICITUD);
            salida.writeObject(stream);

            stream = (Stream) entrada.readObject();

            FXMLSignInController.actualizarInterfazConMensaje(stream.getMensaje().toString());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Fin cliente");
            return stream;
        }
    }
}
