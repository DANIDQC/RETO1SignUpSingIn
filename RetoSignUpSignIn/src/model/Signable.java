/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import retosignupsignin.Client;

/**
 *
 * @author 2dam
 */
public interface Signable {
    public Client signIn() throws Exception;
    public Client signUp() throws Exception;
}