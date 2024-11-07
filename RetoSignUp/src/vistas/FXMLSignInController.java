/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import controlador.SignableFactory;
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
import libreria.Request;
import libreria.Signable;
import libreria.Stream;
import libreria.User;

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

    private boolean fondo = true;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        aplicarFondo();  // Aplica el fondo al inicializar la ventana
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Cambiar fondo a San Francisco");
        item1.setOnAction(this::cambiarFondo);
        MenuItem item2 = new MenuItem("Cambiar fondo a Paris");
        item2.setOnAction(this::cambiarFondo);
        menu.getItems().addAll(item1, item2);
        fxmlLogin.setOnMouseClicked(event -> controlMenu(event, menu));
    }

    private void aplicarFondo() {
        fxmlLogin.setStyle("-fx-background-image: url('" + mainApp.getFondoActual() + "');");
    }

    private void cambiarFondo(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

    private void controlMenu(MouseEvent event, ContextMenu menu) {
        if (event.getButton() == MouseButton.SECONDARY) {
            menu.show(fxmlLogin, event.getScreenX(), event.getScreenY());
        } else {
            menu.hide();
        }
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
    public void iniciarSesion(ActionEvent event) {
        // Obtener email y contraseña de los campos de texto
        String email = txtFieldUsuario.getText();
        String password = txtPasswordField.getText();

        // Validar que los campos no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Inicio de Sesión");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellena todos los campos.");
            alert.showAndWait();
            return;
        }

        // Crear el usuario con la información ingresada
        User user = new User(email, password);

        // Obtener instancia de Signable para autenticación
        Signable signable = SignableFactory.getSignable();

        // Intentar iniciar sesión
        try {
            Stream stream = signable.signIn(user);

            if (stream.getMensaje().equals(Request.OK_SINGIN)) {
                // Si el inicio de sesión es exitoso, abrir ventana de bienvenida
                mainApp.mostrarSignOut(email);  // Llama al método que muestra la ventana de bienvenida
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Inicio de Sesión");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales incorrectas, por favor intente nuevamente.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar con el servidor.");
            alert.showAndWait();
        }
    }

    @FXML
    private void ventanaRegistrarse(ActionEvent event) {
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Registro");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que deseas registrar un usuario nuevo?");

        // Esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, procede a mostrar la ventana de registro
            Stage stage = new Stage();
            try {
                mainApp.mostrarSignUp();
            } catch (Exception ex) {
                Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
