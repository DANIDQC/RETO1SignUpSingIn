/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.Serializable;

/**
 * La clase {@code Stream} representa un flujo de datos que incluye información 
 * de un usuario y un mensaje asociado, utilizada para enviar o recibir datos 
 * en el sistema.
 * @author Guillermo Flecha
 */
public class Stream implements Serializable{
    /**
     * El usuario asociado a este flujo de datos.
     */
    private User usuario;
    
    /**
     * El mensaje asociado a este flujo de datos.
     */
    private Request mensaje;
    /**
     * Crea una nueva instancia de {@code Stream} con el usuario y mensaje especificados.
     *
     * @param usuario el usuario asociado al flujo de datos
     * @param mensaje el mensaje asociado al flujo de datos
     */
    public Stream(User usuario, Request mensaje) {
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    public Stream() {
       
    }

    /**
     * Obtiene el usuario asociado a este flujo de datos.
     *
     * @return el usuario del flujo de datos
     */
    public User getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a este flujo de datos.
     *
     * @param usuario el nuevo usuario del flujo de datos
     */
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el mensaje asociado a este flujo de datos.
     *
     * @return el mensaje del flujo de datos
     */
    public Request getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje asociado a este flujo de datos.
     *
     * @param mensaje el nuevo mensaje del flujo de datos
     */
    public void setMensaje(Request mensaje) {
        this.mensaje = mensaje;
    }
}
