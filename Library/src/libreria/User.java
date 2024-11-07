/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.Serializable;

/**
 * La clase {@code User} representa a un usuario en el sistema, con atributos 
 * de autenticación y datos personales.
 * @author Guillermo Flecha
 */
public class User implements Serializable{
   /**
     * Nombre de usuario o identificador de login.
     */
    private String login;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Estado del usuario, {@code true} si el usuario está activo, {@code false} si está inactivo.
     */
    private boolean active;

    /**
     * Nombre y apellidos del usuario.
     */
    private String nombreApellidos;

    /**
     * Dirección del usuario.
     */
    private String direccion;

    /**
     * Ciudad de residencia del usuario.
     */
    private String ciudad;

    /**
     * Código postal de la dirección del usuario.
     */
    private int codigoPostal;

    /**
     * Constructor de la clase {@code User} que inicializa todos los atributos de un usuario.
     *
     * @param login el nombre de usuario
     * @param password la contraseña del usuario
     * @param active el estado de actividad del usuario
     * @param nombreApellidos el nombre y apellidos del usuario
     * @param direccion la dirección del usuario
     * @param ciudad la ciudad de residencia del usuario
     * @param codigoPostal el código postal de la dirección del usuario
     */
    public User(String login, String password, boolean active, String nombreApellidos, String direccion, String ciudad, int codigoPostal) {
        this.login = login;
        this.password = password;
        this.active = active;
        this.nombreApellidos = nombreApellidos;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }
    
    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User() {
       
    }

    /**
     * Obtiene el nombre de usuario o login.
     *
     * @return el nombre de usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     * Establece el nombre de usuario o login.
     *
     * @param login el nuevo nombre de usuario
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la nueva contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Verifica si el usuario está activo.
     *
     * @return {@code true} si el usuario está activo; {@code false} en caso contrario
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece el estado de actividad del usuario.
     *
     * @param active el nuevo estado de actividad del usuario
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Obtiene el nombre y apellidos del usuario.
     *
     * @return el nombre y apellidos del usuario
     */
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    /**
     * Establece el nombre y apellidos del usuario.
     *
     * @param nombreApellidos el nuevo nombre y apellidos del usuario
     */
    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    /**
     * Obtiene la dirección del usuario.
     *
     * @return la dirección del usuario
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del usuario.
     *
     * @param direccion la nueva dirección del usuario
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la ciudad de residencia del usuario.
     *
     * @return la ciudad del usuario
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad de residencia del usuario.
     *
     * @param ciudad la nueva ciudad del usuario
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el código postal de la dirección del usuario.
     *
     * @return el código postal del usuario
     */
    public int getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal de la dirección del usuario.
     *
     * @param codigoPostal el nuevo código postal del usuario
     */
    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
