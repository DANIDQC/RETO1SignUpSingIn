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
import java.util.ResourceBundle;
import java.util.Scanner;
import libreria.Signable;
import libreria.Stream;
import vistas.FXMLLoginController;
import vistas.FXMLSignUpController;

/**
 *
 * @author Guillermo Flecha
 */
public class Cliente implements Signable{
        Socket socket = null;
        ObjectInputStream entrada = null;
        ObjectOutputStream salida = null;
        
        String IP = ResourceBundle.getBundle("modelo.conexionInfo").getString("IP");
        int PUERTO = Integer.getInteger(ResourceBundle.getBundle("modelo.conexionInfo").getString("PUERTO"));
    

    @Override
    public void signUp(Stream stream) throws Exception {
        try {
            socket = new Socket(IP, PUERTO);
            System.out.println("Esperando que el servidor envíe algo....");
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            
            salida.writeObject(stream);
            
            String mensaje = (String) entrada.readObject();
            
            FXMLSignUpController.actualizarInterfazConMensaje(mensaje);

            
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
        }
    }

    @Override
    public void signIn(Stream stream) throws Exception {
        try {
            socket = new Socket(IP, PUERTO);
            System.out.println("Esperando que el servidor envíe algo....");
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            
            salida.writeObject(stream);
            
            String mensaje = (String) entrada.readObject();
           
            FXMLLoginController.actualizarInterfazConMensaje(mensaje);
            
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
        }
    }
}

