/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Guillermo
 */
public class FXMLLoginController implements Initializable {

    
    @FXML
    private Label label;

    // Campos del FXML
    @FXML
    private PasswordField txtPasswordField;
    @FXML
    private TextField txtFieldContraseña;
    @FXML
    private CheckBox chBoxMostrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Método para manejar la acción del botón de cerrar
    @FXML
    private void cerrarApp(ActionEvent event) {
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma la salida, usar Platform.exit() para cerrar la aplicación
            Platform.exit();
        } else {
            // Si el usuario cancela, no se cierra la aplicación (no es necesario hacer nada aquí)
            event.consume();  // Aunque no es estrictamente necesario, puedes consumir el evento por consistencia
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

    // Método para manejar el botón de alerta
    @FXML
    private void mostrarAlerta(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Autenticación");
        alert.setHeaderText(null);
        alert.setContentText("Usuario y/o contraseña incorrectos.");
        alert.showAndWait();
    }

    public void iniciarSesion(ActionEvent event) throws Exception {
        String nombreUsuario = "NombreDelUsuario"; // Aquí obtendrás el nombre del usuario logueado

        // Iniciar la ventana de bienvenida
        Stage stage = new Stage();
        MainBienvenido bienvenidaApp = new MainBienvenido(nombreUsuario);
        bienvenidaApp.start(stage);

        // Cerrar la ventana de login actual
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }
    
    // Este método se ejecuta cuando el botón es presionado
    @FXML
    private void handleSignUpButtonAction(ActionEvent event) {
        try {
            // Crear una instancia de MainSignUp
            MainSignUp signUp = new MainSignUp();
            
            // Crear un nuevo escenario (Stage) para la ventana de Sign Up
            Stage signUpStage = new Stage();
            
            // Llamar al método start de la ventana SignUp para abrirla
            signUp.start(signUpStage);
            
            // Cerrar la ventana actual de Login
            Stage loginStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            loginStage.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void actualizarInterfazConMensaje(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
