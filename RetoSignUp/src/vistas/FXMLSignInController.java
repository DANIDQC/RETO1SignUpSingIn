/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlador de la ventana de inicio de sesión para la aplicación.
 *
 * Esta clase controla la interfaz de la ventana de inicio de sesión,
 * permitiendo que el usuario interactúe con elementos como el campo de
 * contraseña, el botón de cierre de aplicación, y otros botones que redirigen a
 * otras ventanas.
 *
 * Funcionalidades principales: - Inicializar componentes de la ventana de
 * inicio de sesión. - Mostrar u ocultar la contraseña al seleccionar o
 * deseleccionar el checkbox. - Gestionar el cierre de la aplicación con
 * confirmación del usuario. - Mostrar alertas en caso de error en la
 * autenticación. - Redirigir al usuario a la ventana de bienvenida tras un
 * inicio de sesión exitoso. - Redirigir a la ventana de registro de usuario
 * (Sign Up) al seleccionar el botón correspondiente.
 *
 * Dependencias: - JavaFX: Para gestionar los elementos visuales e interacción
 * con la interfaz. - Clase MainBienvenido: Para iniciar la ventana de
 * bienvenida tras el inicio de sesión. - Clase MainSignUp: Para iniciar la
 * ventana de registro de usuario.
 *
 * @author Guillermo Flecha
 */
public class FXMLSignInController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private PasswordField txtPasswordField;
    @FXML
    private TextField txtFieldContraseña;
    @FXML
    private CheckBox chBoxMostrar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnLogin;
    @FXML
    private BorderPane fxmlLogin;
    @FXML
    private boolean fondo;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.setOnAction(this::iniciarSesion);

        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Cambiar fondo de pantalla a San Francisco.");
        item1.setOnAction(this::cambiarFondoSanFranciso);
        MenuItem item2 = new MenuItem("Cambiar fondo de pantalla a Paris.");
        item2.setOnAction(this::cambiarFondoParis);
        menu.getItems().addAll(item1, item2);
        fxmlLogin.setOnMouseClicked(event -> controlMenu(event, menu));

    }

    public void cambiarFondoParis(ActionEvent event) {
        //Se obtiene el estilo del fondo.
        String estilo = fxmlLogin.getStyle();

        //Se quita la imagen del fondo.
        String estiloNuevo = estilo.replace("-fx-background-image: url\\('.*'\\);", "");

        //Se añade al fondo la imagen con el tema oscuro
        fxmlLogin.setStyle(estiloNuevo + "-fx-background-image: url('/img/fondoPantallaCambiado.jpg');");
        //cambiar el boolean oscuro a true
        fondo = true;
    }

// Método para cambiar el fondo de pantalla a la imagen de París
    public void cambiarFondoSanFranciso(ActionEvent event) {
        //Se obtiene el estilo del fondo.
        String estilo = fxmlLogin.getStyle();

        //Se quita la imagen del fondo.
        String estiloNuevo = estilo.replace("-fx-background-image: url\\('.*'\\);", "");

        //Se añade al fondo la imagen con el tema oscuro
        fxmlLogin.setStyle(estiloNuevo + "-fx-background-image: url('/img/fondoPantalla1.png');");
        //cambiar el boolean oscuro a true
        fondo = true;
    }

    // Método que se ejecuta cuando el checkbox es seleccionado/deseleccionado
    @FXML
    private void mostrarContraseña() {
        if (chBoxMostrar.isSelected()) {
            txtFieldContraseña.setText(txtPasswordField.getText());
            txtFieldContraseña.setVisible(true);
            txtPasswordField.setVisible(false);
        } else {
            txtPasswordField.setText(txtFieldContraseña.getText());
            txtPasswordField.setVisible(true);
            txtFieldContraseña.setVisible(false);
        }
    }
    
    @FXML
    public void iniciarSesion(ActionEvent event){
        String nombreUsuario = "NombreDelUsuario"; // Aquí obtendrás el nombre del usuario logueado

        // Iniciar la ventana de bienvenida
        Stage stage = new Stage();
        try {
            mainApp.mostrarSignOut(txtFieldUsuario.getText());
        } catch (Exception ex) {
            Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ventanaRegistrarse(ActionEvent event) {
        // Iniciar la ventana de bienvenida
        Stage stage = new Stage();
        try {
            mainApp.mostrarSignUp();
        } catch (Exception ex) {
            Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void actualizarInterfazConMensaje(String mensaje) {
        switch(mensaje){
            case "OK_SIGNIN":
                //Vaya a la ventana de SignIn
                break;
            case "EXCEPCION_INTERNA":
                //Alert de expcepcion interna
                break;
            case "LOG_IN_EXCEPCION":
                //Alert Excepción por usuario existente
                break;
            case "EXCEPCION_EN_CONEXIONES":
                //Alert Error en las conexiones del sistema
                break;
        }
    }

    private void controlMenu(MouseEvent event, ContextMenu menu) {
        // Verifica si el clic fue hecho con el botón derecho
        if (event.getButton() == MouseButton.SECONDARY) {
            // Muestra el menú contextual en la posición del clic
            menu.show(fxmlLogin, event.getScreenX(), event.getScreenY());
        } else {
            // Oculta el menú si se hace clic con otro botón
            menu.hide();
        }
    }
}
