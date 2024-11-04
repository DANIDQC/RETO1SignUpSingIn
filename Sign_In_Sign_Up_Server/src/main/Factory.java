/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import libreria.Signable;

/**
 *
 * @author 2dam
 */
public class Factory {

    private static final Pool connectionPool = new Pool();

    // Método factory que crea y devuelve una instancia de Signable
    public static Signable createSignable() {
        return new DAO(connectionPool);
    }
}
