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
public class User {
    private String login;
    private String pass;
    private boolean action;
    private String contra;
    private String street;
    private String city;
    private int zip;

    public User(String login, String pass, boolean action, String contra, String street, String city, int zip) {
        
        this.login = login;
        this.pass = pass;
        this.action = action;
        this.contra = contra;
        this.street = street;
        this.city = city;
        this.zip = zip;
        
    }

    public String getLogin() {
        return login;
    }
    
    public String getPass(){
        return pass;
    }

    public boolean isAction() {
        return action;
    }

    public String getContra() {
        return contra;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setPass(String pass){
        this.pass = pass;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }  
}
