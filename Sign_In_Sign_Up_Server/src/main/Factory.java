/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class Factory {
    public static Signable sign;
    
    public static User action(String message, User user, Connection connection){
        if (message.equals("SignIn")){
            try {
                return sign.signIn(user, connection);
            } catch (Exception ex) {
                message = "loginError";
            }
        } else if (message.equals("SignUp")){
            return sign.signUp(user, connection);
        } else{
            message = "ActionError";
        }
        return user;
    }
}
