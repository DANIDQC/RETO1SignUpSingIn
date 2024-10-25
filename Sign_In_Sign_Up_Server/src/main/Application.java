package main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final int PUERTO = 50100;
    private static final List<Thread> workerThreads = new ArrayList<>();
    private static volatile boolean isRunning = true;

    public static void main(String[] args) throws SQLException {
        Pool connectionPool = new Pool(3);
        Signable sign = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor escuchando en el puerto " + PUERTO);

            Thread keyboardReaderThread = new Thread(new KeyboardEscListener());
            keyboardReaderThread.start();

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                Worker worker = new Worker(clientSocket, connectionPool, sign);
                Thread workerThread = new Thread(worker);
                workerThreads.add(workerThread);
                workerThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connectionPool.closeAllConnections();
            new Thread(new ThreadTerminator()).start();
        }
    }

    static class KeyboardEscListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    if (System.in.available() > 0) {
                        int key = System.in.read();
                        if (key == 27) {
                            System.out.println("Tecla Esc presionada. Cerrando el servidor y todos los hilos...");
                            isRunning = false;
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadTerminator implements Runnable {
        @Override
        public void run() {
            for (Thread workerThread : workerThreads) {
                if (workerThread.isAlive()) {
                    workerThread.interrupt();
                }
            }
            System.out.println("Todos los hilos de trabajo han sido interrumpidos.");
            System.exit(0);
        }
    }
}
