/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import factorias.FactorySignable;
import java.util.logging.Level;
import java.util.logging.Logger;
import retosignupsignin.Client;

/**
 *
 * @author 2dam
 */
public class SignInController {
    Client signIn;

    public SignInController() {
        try {
            this.signIn = FactorySignable.getSignable().signIn();
        } catch (Exception ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
