/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import java.io.IOException;
import main.Application;

public class KeyListenerThread implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                int keyCode = System.in.read();
                if (keyCode == 27) { // Código ASCII de la tecla ESC
                    System.out.println("Tecla ESC presionada. Cerrando el servidor...");
                    Application.closeAllThreads();
                    break; // Sale del bucle para detener el KeyListenerThread
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
