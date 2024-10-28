/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Clase de prueba para la vista y el controlador de inicio de sesión. Prueba el
 * comportamiento de la vista de inicio de sesión utilizando el framework
 * TestFX.
 *
 * @autor Guillermo Flecha
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FXMLLoginControllerTest extends ApplicationTest {

    /**
     * Inicia la aplicación a probar.
     *
     * @param stage Objeto de la etapa principal (Primary Stage)
     * @throws Exception Si ocurre algún error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new  MainLogin().start(stage);
    }
    
    @Test
    public void test_LoginEsActivo(){
        clickOn("#txtFieldUsuario");
        write("diu@gmail.com");
        clickOn("#txtPasswordField");
        write("abcd*1234");
        
        clickOn("#chBoxMostrar");
        
        clickOn("#btnSalir");
        clickOn("Cancelar");
        
        clickOn("#btnRegistrarse");
        verifyThat("#fxmlSignUp", isVisible());
        
        clickOn("#idNombreApellido");
        write("Guillermo Flecha Vega");
        
        clickOn("#btnSalirApp");
        clickOn("Aceptar");
    }
}
