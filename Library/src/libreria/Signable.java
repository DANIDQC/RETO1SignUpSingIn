/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

/**
 *
 * @author Guillermo
 */
public interface Signable {

    /**
     *
     * @param stream
     * @throws Exception
     */
    public void signUp(Stream stream) throws Exception;
    public void signIn(Stream stream) throws Exception;
    
}
