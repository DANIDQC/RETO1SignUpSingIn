/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import libreria.Signable;

/**
 *
 * @author Guillermo
 */
public class SignableFactory {
    
    public static Signable getSignable(){
        return new Cliente();
    } 
}
