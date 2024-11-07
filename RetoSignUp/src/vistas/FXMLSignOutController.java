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

public class FXMLSignOutController {

    @FXML
    private Label lblUser;
    @FXML
    private Pane fxmlBienvenido;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setUserName(String username) {
        lblUser.setText(username != null && !username.isEmpty() ? username : "Usuario no encontrado");
    }

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
