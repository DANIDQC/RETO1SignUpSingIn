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
 * Controlador para la vista de inicio de sesión de la aplicación. 
 * Administra el proceso de autenticación de usuarios, incluyendo la 
 * opción de mostrar u ocultar contraseñas y cambiar el fondo de la interfaz.
 */
public class FXMLSignInController implements Initializable {

    /**
     * Etiqueta que muestra el mensaje de bienvenida.
     */
    @FXML
    private Label label;

    /**
     * Campo de texto para ingresar el nombre de usuario o email.
     */
    @FXML
    private TextField txtFieldUsuario;

    /**
     * Campo de texto de tipo contraseña para ingresar la contraseña.
     */
    @FXML
    private PasswordField txtPasswordField;

    /**
     * Campo de texto que permite visualizar la contraseña en texto claro cuando es necesario.
     */
    @FXML
    private TextField txtFieldContraseña;

    /**
     * Casilla de verificación para habilitar la visualización de la contraseña en texto claro.
     */
    @FXML
    private CheckBox chBoxMostrar;

    /**
     * Botón para cerrar la aplicación.
     */
    @FXML
    private Button btnSalir;

    /**
     * Botón para iniciar sesión.
     */
    @FXML
    private Button btnLogin;

    /**
     * Contenedor de tipo BorderPane para la vista principal de inicio de sesión.
     */
    @FXML
    private BorderPane fxmlLogin;

    /**
     * Bandera que indica si el fondo actual es el predeterminado.
     */
    private boolean fondo = true;

    /**
     * Referencia a la clase principal de la aplicación, utilizada para la navegación entre pantallas.
     */
    private Main mainApp;

    /**
     * Establece la referencia a la aplicación principal y aplica el fondo a la interfaz.
     * 
     * @param mainApp La aplicación principal que maneja la navegación.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        aplicarFondo();  // Aplica el fondo al inicializar la ventana
    }

    /**
     * Inicializa la interfaz, configurando un menú contextual para cambiar el fondo.
     * 
     * @param url No se usa.
     * @param rb No se usa.
     */
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

    /**
     * Aplica el fondo actual a la interfaz de usuario.
     */
    private void aplicarFondo() {
        fxmlLogin.setStyle("-fx-background-image: url('" + mainApp.getFondoActual() + "');");
    }

    /**
     * Cambia el fondo de la interfaz de usuario según la opción seleccionada en el menú contextual.
     * 
     * @param event Evento que desencadena el cambio de fondo.
     */
    private void cambiarFondo(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

    /**
     * Controla el menú contextual, mostrando el menú al hacer clic con el botón derecho.
     * 
     * @param event Evento de clic de ratón.
     * @param menu Menú contextual para cambiar el fondo.
     */
    private void controlMenu(MouseEvent event, ContextMenu menu) {
        if (event.getButton() == MouseButton.SECONDARY) {
            menu.show(fxmlLogin, event.getScreenX(), event.getScreenY());
        } else {
            menu.hide();
        }
    }

    /**
     * Muestra u oculta la contraseña en texto claro cuando se selecciona la casilla de verificación.
     */
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

    /**
     * Maneja el proceso de inicio de sesión, verificando credenciales y mostrando alertas de error en caso de fallo.
     * 
     * @param event Evento que desencadena el inicio de sesión.
     */
    @FXML
    public void iniciarSesion(ActionEvent event) {
        String email = txtFieldUsuario.getText();
        String password = txtPasswordField.getText();

        // Validación de campos
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Inicio de Sesión");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellena todos los campos.");
            alert.showAndWait();
            return;
        }

        // Intento de autenticación
        User user = new User(email, password);
        Signable signable = SignableFactory.getSignable();

        try {
            Stream stream = signable.signIn(user);

            if (stream.getMensaje().equals(Request.OK_SINGIN)) {
                mainApp.mostrarSignOut(email);
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

    /**
     * Muestra una ventana de confirmación para proceder al registro de un nuevo usuario.
     * Si el usuario confirma, se muestra la ventana de registro.
     * 
     * @param event Evento que desencadena la ventana de registro.
     */
    @FXML
    private void ventanaRegistrarse(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Registro");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que deseas registrar un usuario nuevo?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = new Stage();
            try {
                mainApp.mostrarSignUp();
            } catch (Exception ex) {
                Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}