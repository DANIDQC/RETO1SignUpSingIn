package hilos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Factory;
import libreria.Signable;
import libreria.User;
import libreria.Stream;

/**
 * La clase {Worker} representa un hilo que maneja una conexión de cliente
 * en un servidor. Este hilo se encarga de procesar solicitudes de los clientes,
 * como registros de usuario o inicio de sesión, y devolver respuestas correspondientes.
 * 
 * El hilo recibe objetos a través de {ObjectInputStream} y envía respuestas
 * mediante {ObjectOutputStream}. Los mensajes se procesan según el tipo de
 * solicitud recibida, ya sea de inicio de sesión o de registro.
 * 
 * La clase hereda de {Thread}, lo que permite que cada cliente sea atendido
 * en un hilo separado. El cliente interactúa con el servidor enviando objetos
 * de tipo {Stream}, que contienen tanto el mensaje a procesar como los
 * datos del usuario.
 * 
 * @author Asier del Campo.
 */
public class Worker extends Thread {
    private final Socket clientSocket;
    private Signable sign;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    /**
     * Constructor de la clase {@code Worker}.
     * 
     * @param clientSocket El socket de la conexión del cliente.
     * @param in El {ObjectInputStream} para recibir objetos del cliente.
     * @param out El {ObjectOutputStream} para enviar respuestas al cliente.
     */
    public Worker(Socket clientSocket, ObjectInputStream in, ObjectOutputStream out) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.start();
    }

    /**
     * Método que ejecuta el hilo y procesa las solicitudes del cliente.
     * 
     * El método lee un objeto {Stream} del cliente, obtiene el mensaje y el
     * usuario, y realiza una acción en función del tipo de solicitud recibida
     * ("SIGN_IN_SOLICITUD" o "SIGN_UP_SOLICITUD"). Según la solicitud, se llama
     * al método correspondiente del objeto {Signable} para realizar el inicio
     * de sesión o el registro. La respuesta se envía de vuelta al cliente mediante
     * el {ObjectOutputStream}.
     * 
     * Si ocurre una excepción, se captura y se registra un mensaje de error.
     * Finalmente, se cierra el socket de cliente al terminar el proceso.
     * 
     * @see Signable#signIn(User)
     * @see Signable#signUp(User)
     */
    @Override
    public void run() {
        try {
            Stream stream = (Stream) in.readObject();
            String message = stream.getMensaje().toString();
            User user = (User) stream.getUsuario();
            Signable signable = Factory.createSignable();

            try {
                if ("SIGN_IN_SOLICITUD".equalsIgnoreCase(message)) {
                    stream = signable.signIn(user);
                } else if ("SIGN_UP_SOLICITUD".equalsIgnoreCase(message)) {
                    stream = signable.signUp(user);
                }
            } catch (Exception ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.writeObject(stream);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


