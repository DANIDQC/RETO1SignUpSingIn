package main;

import hilos.KeyListenerThread;
import hilos.Worker;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La clase {@code Application} es el punto de entrada principal para iniciar el servidor.
 * Esta clase configura el servidor para escuchar en un puerto específico, aceptar conexiones
 * de clientes y crear hilos para gestionar la comunicación con cada cliente.
 * 
 * El servidor puede manejar múltiples conexiones de clientes de manera concurrente, con un
 * número máximo de hilos definidos por la constante {MAX_THREADS}. Además, un hilo
 * dedicado escucha la tecla ESC para cerrar el servidor y todos los hilos en ejecución.
 * 
 * El servidor se ejecuta continuamente, esperando conexiones y asignando un hilo para cada
 * cliente que se conecta, mientras gestiona el límite de hilos activos en el sistema.
 * 
 * @author Asier del Campo.
 */
public class Application {
    private static final int PORT = 50250;
    private static final int MAX_THREADS = 10; // Máximo de 10 hilos a la vez
    private static final Thread[] threadArray = new Thread[MAX_THREADS];
    private static ServerSocket serverSocket;

    /**
     * Método principal que inicia el servidor, acepta conexiones de clientes y asigna hilos
     * para gestionar la comunicación con los clientes.
     * 
     * El servidor se inicia en el puerto {PORT} y comienza a escuchar por conexiones.
     * Cada vez que un cliente se conecta, se crea un nuevo {Worker} en un hilo disponible
     * para manejar la solicitud del cliente. También se inicia un hilo para escuchar la tecla ESC,
     * lo que permite cerrar el servidor y detener todos los hilos en ejecución cuando se presiona dicha tecla.
     * 
     * @param args Los argumentos de línea de comandos (no utilizados en este caso).
     * 
     */
    public static void main(String[] args) {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        InputStream entrada = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado y escuchando en el puerto " + PORT);

            Thread keyListenerThread = new Thread(new KeyListenerThread());
            keyListenerThread.start();

            while (true) {
                // Acepta la conexión de un cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                entrada = clientSocket.getInputStream();
                in = new ObjectInputStream(entrada);
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                int index = findAvailableSlot();
                if (index == -1) {
                    System.out.println("Máximo de conexiones alcanzado. Esperando espacio...");
                    Thread.sleep(1000); 
                } else {
                    threadArray[index] = new Worker(clientSocket, in, out);
                    //threadArray[index].start(); 
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca el primer espacio disponible en el array {threadArray} para crear un nuevo hilo.
     * 
     * Este método recorre el array de hilos y devuelve el índice del primer espacio disponible
     * (ya sea porque el hilo está vacío o el hilo ya ha terminado su ejecución). Si no se encuentra
     * espacio disponible, devuelve {-1}.
     * 
     * @return El índice del primer espacio disponible en el array, o {-1} si no hay espacio.
     */
    private static int findAvailableSlot() {
        for (int i = 0; i < MAX_THREADS; i++) {
            if (threadArray[i] == null || !threadArray[i].isAlive()) {
                return i;
            }
        }
        return -1; 
    }

    /**
     * Cierra todos los hilos activos y el servidor.
     * 
     * Este método cierra el {ServerSocket} y detiene todos los hilos que están gestionando
     * a los clientes. Cada hilo es interrumpido y la referencia al hilo en el array es limpiada.
     * Se asegura de que el servidor se cierre correctamente y no haya hilos en ejecución al finalizar.
     */
    public static void closeAllThreads() {
        try {
            // Cierra el servidor si está activo
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            // Interrumpe y limpia todos los hilos
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



