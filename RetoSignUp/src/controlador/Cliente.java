package controlador;

import excepciones.ExcepcionConexionesError;
import excepciones.ExcepcionInternaServidorError;
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
 * La clase {Cliente} implementa la interfaz {Signable} y se encarga de manejar
 * las operaciones de conexión a un servidor para la autenticación y registro de usuarios
 * en una aplicación cliente-servidor. Se conecta al servidor a través de un socket y envía
 * solicitudes de inicio de sesión o registro. Además, recibe las respuestas del servidor 
 * para realizar las acciones correspondientes.
 * 
 * Esta clase gestiona tanto el flujo de datos de entrada como el de salida entre el cliente
 * y el servidor a través de objetos {ObjectInputStream} y {ObjectOutputStream}.
 * 
 * @author Guillermo Flecha
 */
public class Cliente implements Signable {

    private static ServerSocket serverSocket;

    /**
     * Socket utilizado para la conexión del cliente con el servidor.
     */
    Socket socket = null;

    /**
     * Flujo de entrada utilizado para recibir datos del servidor.
     */
    ObjectInputStream entrada = null;

    /**
     * Flujo de salida utilizado para enviar datos al servidor.
     */
    ObjectOutputStream salida = null;

    /**
     * Dirección IP del servidor, obtenida desde un archivo de configuración.
     */
    String IP = ResourceBundle.getBundle("modelo.conexionInfo").getString("IP");

    /**
     * Puerto del servidor, obtenido desde un archivo de configuración.
     */
    int PUERTO = Integer.parseInt(ResourceBundle.getBundle("modelo.conexionInfo").getString("PUERTO"));

    /**
     * Realiza una solicitud de registro de usuario al servidor.
     * 
     * Este método establece una conexión con el servidor, envía la solicitud de registro
     * del usuario y espera la respuesta del servidor. El flujo de datos se maneja a través
     * de los objetos {ObjectInputStream} y {ObjectOutputStream}.
     *
     * @param user El objeto {User} que contiene la información del usuario a registrar.
     * @return Un objeto {Stream} con la respuesta del servidor con respecto al registro.
     * @throws Exception Si ocurre algún error durante el proceso de conexión o durante la
     *                   comunicación con el servidor.
     */
    @Override
    public Stream signUp(User user) throws Exception {
        Stream stream = null;
        Socket socket = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;

        try {
            // Conectar al servidor
            socket = new Socket(IP, PUERTO);
            System.out.println("Conectado al servidor, enviando solicitud de registro...");

            // Configurar flujos de entrada y salida
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            // Crear y enviar la solicitud de registro
            stream = new Stream(user, Request.SIGN_UP_SOLICITUD);
            salida.writeObject(stream);

            // Esperar y devolver la respuesta del servidor
            stream = (Stream) entrada.readObject();
            return stream;

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return new Stream(user, Request.EXCEPCION_EN_CONEXIONES); 
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return new Stream(user, Request.EXCEPCION_EN_CONEXIONES); 
        } finally {
            // Cerrar los recursos
            try {
                if (entrada != null) {
                    entrada.close();
                }
                if (salida != null) {
                    salida.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
            System.out.println("Fin cliente");
        }
    }

    /**
     * Realiza una solicitud de inicio de sesión de usuario al servidor.
     * 
     * Este método establece una conexión con el servidor, envía la solicitud de inicio de sesión
     * del usuario y espera la respuesta del servidor. El flujo de datos se maneja a través de los
     * objetos {ObjectInputStream} y {ObjectOutputStream}.
     *
     * @param user El objeto {User} que contiene la información del usuario que intenta iniciar sesión.
     * @return Un objeto {Stream} con la respuesta del servidor con respecto al inicio de sesión.
     * @throws Exception Si ocurre algún error durante el proceso de conexión o durante la comunicación
     *                   con el servidor.
     */
    @Override
    public Stream signIn(User user) throws Exception {
        Stream stream = null;

        try {
            // Conectar al servidor
            socket = new Socket(IP, PUERTO);
            System.out.println("Esperando que el servidor envíe algo....");

            // Configurar flujos de entrada y salida
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            // Crear y enviar la solicitud de inicio de sesión
            stream = new Stream(user, Request.SIGN_IN_SOLICITUD);
            salida.writeObject(stream);

            // Esperar la respuesta del servidor
            stream = (Stream) entrada.readObject();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Cerrar los recursos
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
