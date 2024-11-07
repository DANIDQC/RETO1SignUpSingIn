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


public class Worker extends Thread {
    private final Socket clientSocket;
    private Signable sign;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public Worker(Socket clientSocket, ObjectInputStream in, ObjectOutputStream out) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.start();
    }

    @Override
    public void run() {
        try {
                System.out.println("He entrado en el Threadddddddddddddddddddddddddddddddddddddddddddddddddddd");
                Stream stream = (Stream) in.readObject();
                String message = stream.getMensaje().toString();
                User user = (User) stream.getUsuario();
                Signable signable = Factory.createSignable();
                try{
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

