/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Esta es la excepción que salta cuando hay un error interno en el servidor.
 * @author Guillermo Flecha
 */
public class ExcepcionInternaServidorError extends Exception {

    /**
     * Llamamos al método ExcepcionInternaServidorError cuando hacemos el throw 
     * y mostramos un mensaje de error.
     * 
     */
    public ExcepcionInternaServidorError() {
        super("Error interno del servidor.");
    }
}
