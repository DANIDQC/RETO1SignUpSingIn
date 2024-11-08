package hilos;

import java.io.IOException;
import main.Application;

/**
 * Clase que implementa un hilo que escucha las teclas presionadas en el teclado.
 * Se ejecuta en un bucle infinito y espera a que se presione la tecla ESC (código ASCII 27).
 * Cuando se detecta la tecla ESC, el hilo cierra todos los hilos de la aplicación
 * y termina su ejecución.
 * La clase implementa la interfaz {Runnable}, por lo que puede ser ejecutada
 * en un hilo independiente.
 * 
 * @author Asier del Campo.
 */
public class KeyListenerThread implements Runnable {

    /**
     * Ejecuta el hilo que escucha las teclas presionadas. Cuando la tecla ESC (código ASCII 27)
     * es presionada, cierra todos los hilos de la aplicación y termina la ejecución del hilo
     * {KeyListenerThread}.
     * El método lee los caracteres introducidos por el usuario desde {@code System.in} y verifica
     * si se presiona la tecla ESC. En caso afirmativo, se invoca el método {@link Application#closeAllThreads()}
     * para cerrar todos los hilos y luego se detiene el bucle que ejecuta el hilo.
     * 
     * @see Application#closeAllThreads()
     */
    @Override
    public void run() {
        try {
            while (true) {
                int keyCode = System.in.read();
                if (keyCode == 27) {
                    System.out.println("Tecla ESC presionada. Cerrando el servidor...");
                    Application.closeAllThreads();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

