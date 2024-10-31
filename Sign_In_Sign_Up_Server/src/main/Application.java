package main;

import hilos.KeyListenerThread;
import hilos.Worker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {
    private static final int PORT = 50100;
    private static final int MAX_THREADS = 10; // Máximo de 10 hilos a la vez
    private static final Thread[] threadArray = new Thread[MAX_THREADS];
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado y escuchando en el puerto " + PORT);

            // Inicia el hilo para escuchar la tecla ESC y cerrar el servidor
            Thread keyListenerThread = new Thread(new KeyListenerThread());
            keyListenerThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                int index = findAvailableSlot();
                if (index == -1) {
                    System.out.println("Máximo de conexiones alcanzado. Esperando espacio...");
                    Thread.sleep(1000); // Espera un momento antes de volver a intentar
                } else {
                    // Asigna un nuevo hilo en el espacio disponible
                    threadArray[index] = new Worker(clientSocket);
                    threadArray[index].start();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para encontrar el primer espacio disponible en el array
    private static int findAvailableSlot() {
        for (int i = 0; i < MAX_THREADS; i++) {
            if (threadArray[i] == null || !threadArray[i].isAlive()) {
                return i;
            }
        }
        return -1; // No hay espacios disponibles
    }

    // Método para cerrar todos los hilos y el servidor
    public static void closeAllThreads() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (int i = 0; i < MAX_THREADS; i++) {
                if (threadArray[i] != null) {
                    threadArray[i].interrupt(); // Interrumpe cada hilo
                    threadArray[i] = null; // Limpia la referencia en el array
                }
            }
            System.out.println("Todos los hilos y el servidor han sido cerrados.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


