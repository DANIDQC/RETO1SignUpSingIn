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
 * @autor Andoni Gracia
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
        new  Main().start(stage);
    }
    
    @Test
    public void test_LoginEsActivo(){
        clickOn("#txtFieldUsuario");
        write("diu@gmail.com");
        clickOn("#txtPasswordField");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat("#fxmlBienvenido", isVisible());
    }

    @Test
    public void test_RegistrarExistente() {
        clickOn("#btnRegistrarse");
        verifyThat("#fxmlSignUp", isVisible());

        clickOn("#idNombreApellido");
        write("Andoni Garcia Alvarez");
        clickOn("#idDireccion");
        write("Gran Via N8");
        clickOn("#idCiudad");
        write("Bilbao");
        clickOn("#idCodigoPostal");
        write("48011");
        clickOn("#idCorreoElectronico");
        write("diu@gmail.com");
        clickOn("#idContrasena");
        write("abcd*1234");
        clickOn("#idRepetirContrasena");
        write("abcd*1234");
        clickOn("#idActivo");
        clickOn("#btnRegistrarse");
        verifyThat("Ya existe un usuario con este gmail.", isVisible());
    }
}
