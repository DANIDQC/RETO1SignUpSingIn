/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.Serializable;

/**
 * Enumeración que representa diferentes tipos de solicitudes que son enviados al servidor,
 * mediante excepciones y las interfaces.
 * @author Guillermo Flecha
 */
public enum Request implements Serializable{
   /**
     * Solicitud de registro de usuario en el sistema.
     * Representa una solicitud para crear un nuevo usuario.
     */
    SIGN_UP_SOLICITUD,
    
    /**
     * Solicitud de inicio de sesión de un usuario en el sistema.
     * Representa una solicitud para autenticar a un usuario existente.
     */
    SIGN_IN_SOLICITUD,
    
    /**
     * Error interno en el sistema.
     * Se utiliza para indicar que ocurrió un error general o desconocido.
     */
    EXCEPCION_INTERNA,
    
    /**
     * Excepción durante el inicio de sesión.
     * Representa un error específico ocurrido durante el proceso de autenticación.
     */
    LOG_IN_EXCEPCION,
    
    /**
     * Error en las conexiones del sistema.
     * Se usa para indicar que se ha producido un problema con la conexión a recursos externos.
     */
    EXCEPCION_EN_CONEXIONES,
    
    /**
     * Excepción por usuario existente.
     * Indica que el usuario que se intenta registrar ya existe en el sistema.
     */
    USUARIO_EXISTE_EXCEPCION,
    
      /**
     * Indica que el inicio de sesión fue exitoso.
     * Representa una confirmación de que el usuario ha sido autenticado correctamente.
     */
    OK_SINGIN,
    
    
     /**
     * Indica que el registro fue exitoso.
     * Representa una confirmación de que el registro ha sido autenticado correctamente.
     */
    OK_SINGUP;
}
