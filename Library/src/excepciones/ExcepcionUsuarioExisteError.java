/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Esta es una clase tipo excepción en la cual llamamos al método
 * error de usuario existente y mostramos un mensaje al comprobar que el 
 * usuario que estamos intentando introducir ya existe.
 * @author Guillermo Flecha
 */
public class ExcepcionUsuarioExisteError extends Exception {

    /**
     * Llamamos al método ExcepcionUsuarioExisteError cuando hacemos el throw 
     * y mostramos un mensaje de error.
     * 
     */
    public ExcepcionUsuarioExisteError() {
        super("Error el usuario que estas introduciendo ya existe.");
    }
}
