/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Esta clase es tipo excepción y la estamos utilizando para llamar al método
 * error de conexiones cuando falle la conexión entre servidor y cliente.
 * @author Guillermo Flecha
 */
public class ExcepcionConexionesError extends Exception {

    /**
     * Llamamos al método ExcepcionConexionesError cuando hacemos el throw 
     * y mostramos un mensaje de error.
     * 
     */
    public ExcepcionConexionesError() {
        super("Error en la conexión al servidor.");
    }
}
