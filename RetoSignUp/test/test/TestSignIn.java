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

public class TestSignIn extends ApplicationTest {

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
    public void btest_LoginEsActivo() {
        clickOn("#txtFieldUsuario");
        write("diu@gmail.com");
        clickOn("#txtPasswordField");
        write("abcd");
        clickOn("#btnLogin");
        verifyThat("#fxmlBienvenido", isVisible());
        sleep(2000);
    }



    @Test
    public void dtest_ServerEmailOrPassword() {
        clickOn("#txtFieldUsuario");
        write("diu@gmail.com");
        clickOn("#txtPasswordField");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat("Credenciales incorrectas, por favor intente nuevamente.", isVisible());
    }

}
