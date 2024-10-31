package hilos;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Factory;
import main.Signable;
import main.User;


public class Worker extends Thread {
    private final Socket clientSocket;
    private Signable sign;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            while (!Thread.currentThread().isInterrupted()) { // Revisa si el hilo ha sido interrumpido
                String message = (String) in.readObject(); // Lee el mensaje ("SignIn" o "SignUp")
                User user = (User) in.readObject(); // Lee el objeto User

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

                out.writeObject("Operación " + message + " completada para el usuario: " + user.getNombre());
            }

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

