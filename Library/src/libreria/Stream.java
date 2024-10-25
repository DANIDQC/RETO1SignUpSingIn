/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.Serializable;

/**
 *
 * @author Guillermo
 */
public class Stream implements Serializable{
    private User usuario;
    private String mensaje;
    
    public Stream(User usuario, String mensaje){
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
