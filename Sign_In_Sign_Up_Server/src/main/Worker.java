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
public class Worker extends Thread {

    private int n;
    private User usuario;
    private String message;

    Worker(User usuario, String message) {
        this.usuario = usuario;
        this.message = message;
        this.start();
    }

    public void run() {
        Signable sign = null;
        if (message.equals("SignIn")) {
            sign.signIn(usuario);
        } else if (message.equals("SignUp")) {
            sign.signUp(usuario);
        } else {
            System.out.println("ERROR");
        }

    }
}
