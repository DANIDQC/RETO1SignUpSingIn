/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorias;

import controladores.SignInController;
import controladores.SignUpController;
import model.Signable;
import retosignupsignin.Client;

/**
 *
 * @author 2dam
 */

public class FactorySignable {
    public static Signable signable;
    public static Client getSignable() throws Exception{
        boolean userCorrect=true;
        if (userCorrect) {
            signable = new SignInController();
            return signable.signIn(); 
        } else {
            signable = new SignUpController();
            return signable.signUp(); 
        }
    }
}
