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
    }

    @Override
    public void run() {
        try {
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                Stream stream = (Stream) in.readObject();
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                String message = stream.getMensaje().toString();
                User user = (User) stream.getUsuario();
                System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccc");
                Signable signable = Factory.createSignable();
                try{
                if ("SignIn".equalsIgnoreCase(message)) {
                    signable.signIn(user);
                } else if ("SignUp".equalsIgnoreCase(message)) {
                    signable.signUp(user);
                }
                } catch (Exception ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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

