/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
/**
 *
 * @author 2dam
 */
public class Factory {
    public static Signable sign;
    
    public static User action(String message, User user){
        if (message.equals("SignIn")){
            return sign.signIn(user);
        } else if (message.equals("SignUp")){
            return sign.signUp(user);
        } else{
            message = "ActionError";
            return null;
        }
    }
}
