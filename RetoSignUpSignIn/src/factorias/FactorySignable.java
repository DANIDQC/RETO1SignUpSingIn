/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorias;

import interfaces.Signable;

/**
 *
 * @author 2dam
 */

public class FactorySignable {
    
    public static Signable getSignable(){
        boolean userCorrect=true;
        if (userCorrect) {
            return new Signable();
        } else {
            System.out.println("Error al loggearse.");
            return new Signable(); 
        }
    }
}
