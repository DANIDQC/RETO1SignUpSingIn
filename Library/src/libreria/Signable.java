/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

/**
 * La interfaz {@code Signable} define métodos para el registro e inicio de sesión 
 * de usuarios en el sistema.
 * @author Guillermo Flecha
 */
public interface Signable {

     /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param stream el objeto {@code Stream} que contiene la información del usuario 
     *               y otros datos necesarios para el registro
     * @throws Exception si ocurre un error durante el proceso de registro
     */
    public void signUp(Stream stream) throws Exception;

    /**
     * Inicia sesión de un usuario en el sistema.
     *
     * @param stream el objeto {@code Stream} que contiene la información del usuario 
     *               necesaria para la autenticación
     * @throws Exception si ocurre un error durante el proceso de inicio de sesión
     */
    public void signIn(Stream stream) throws Exception;
    
}
