/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import vistas.Main;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Andoni
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestServerError extends ApplicationTest {

    /**
     * Inicia la aplicación a probar.
     *
     * @param stage Objeto de la etapa principal (Primary Stage)
     * @throws Exception Si ocurre algún error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

 
     @Test
    public void etest_ServerBackendSignUp() {
        
        clickOn("#btnRegistrarse");
        verifyThat("¿Seguro que deseas registrar un usuario nuevo?", isVisible());
        clickOn(ButtonType.OK.getText());
        verifyThat("#fxmlSignUp", isVisible());
        clickOn("#idNombreApellido");
        write("Andoni Garcia");
        clickOn("#idDireccion");
        write("Gran Via 10");
        clickOn("#idCiudad");
        write("Portugalete");
        clickOn("#idCodigoPostal");
        write("48920");
        clickOn("#idCorreoElectronico");
        write("diu@gmail.com");
        clickOn("#idContrasena");
        write("abcd");
        clickOn("#idRepetirContrasena");
        write("abcd");
        clickOn("#btnRegistrarse");
        verifyThat("Ha ocurrido un error con las conexiones", isVisible());
        clickOn("Aceptar");
    }
     @Test
    public void ftest_ServerBackendLogin() {
        clickOn("#txtFieldUsuario");
        write("diu@gmail.com");
        clickOn("#txtPasswordField");
        write("abcd");
        clickOn("#btnLogin");
        verifyThat("No se pudo conectar con el servidor.", isVisible());
    }

}
