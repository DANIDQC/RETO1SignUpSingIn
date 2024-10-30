package vistas;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLSignOutController {

    @FXML
    private Label lblUser; 
    
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    // Método para cambiar el texto del Label lblUser
    public void setUserName(String username) {
        if (username != null && !username.isEmpty()) {
            lblUser.setText(username);
        } else {
            lblUser.setText("Usuario no encontrado");
        } 
    }

    @FXML
    public void cerrarAplicacion(ActionEvent event) {
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");
        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma la sali+a, usar Platform.exit() para cerrar la aplicación
            Platform.exit();
        } else {
            // Si el usuario cancela, no se cierra la aplicación
            event.consume();  // Consumiendo el evento
        }
    }

    // Método que se ejecuta cuando se hace clic en "Desloguearse"
    @FXML
    public void irALogin(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana (Stage) para el login
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");

            // Configurar la escena con el root cargado
            Scene scene = new Scene(root);
            loginStage.setScene(scene);

            // Hacer que la ventana de login sea modal
            loginStage.initModality(Modality.APPLICATION_MODAL);

            // Mostrar la ventana de login
            loginStage.show();

            // Cerrar la ventana de bienvenida actual
            Stage currentStage = (Stage) lblUser.getScene().getWindow();  // Obtener el Stage actual a través de un nodo de la ventana
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
