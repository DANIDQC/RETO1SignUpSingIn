/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import model.Signable;
import retosignupsignin.Client;

/**
 *
 * @author 2dam
 */
public class SignInController implements Signable{
    Client signIn;

    public SignInController() {
       
    }

    @Override
    public Client signIn() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client signUp() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}