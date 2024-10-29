/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Esta clase es una clase expeción la cual la utilizamos para mostrar un error,
 * a la hora de comprobar el Login.
 * @author Guillermo Flecha
 */
public class ExcepcionLoginError extends Exception {

    /**
     * Llamamos al método ExcepcionLoginError cuando hacemos el throw 
     * y mostramos un mesaje error.
     * 
     */
    public ExcepcionLoginError() {
        super("Error al hacer el Login.");
        
    }
}
