package vistas;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Controlador para la vista de salida de la aplicación. Gestiona las acciones de
 * cierre de sesión y salida de la aplicación, así como la visualización del
 * nombre de usuario.
 * 
 * @author Guillermo Flecha , Daniel Quintana. 
 */
public class FXMLSignOutController {

    /**
     * Etiqueta que muestra el nombre del usuario en la interfaz.
     */
    @FXML
    private Label lblUser;
    /**
     * Panel principal que contiene los elementos de la vista de bienvenida.
     */
    @FXML
    private Pane fxmlBienvenido;

    /**
     * Instancia de la clase principal de la aplicación, utilizada para manejar
     * la navegación entre pantallas.
     */
    private Main mainApp;

    /**
     * Establece la instancia principal de la aplicación.
     * 
     * @param mainApp La aplicación principal que maneja la interfaz y navegación.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Establece el nombre del usuario en la etiqueta correspondiente. Si el nombre
     * es nulo o vacío, se muestra "Usuario no encontrado".
     * 
     * @param username El nombre del usuario a mostrar.
     */
    public void setUserName(String username) {
        lblUser.setText(username != null && !username.isEmpty() ? username : "Usuario no encontrado");
    }

    /**
     * Maneja la acción de cierre de la aplicación. Muestra una alerta de confirmación
     * antes de cerrar la aplicación si el usuario confirma la acción.
     * 
     * @param event El evento que desencadena la acción de cerrar la aplicación.
     */
    @FXML
    public void cerrarAplicacion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    /**
     * Maneja la acción para volver a la pantalla de inicio de sesión.
     * 
     * @param event El evento que desencadena la acción para ir a la pantalla de login.
     */
    @FXML
    public void irALogin(ActionEvent event) {
        if (mainApp != null) {
            try {
                mainApp.mostrarLogin();
            } catch (Exception ex) {
                Logger.getLogger(FXMLSignOutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
