package controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BienvenidaController {
    
    // Vincula el Label desde el FXML
    @FXML
    private Label lblUser;

    // Método para cambiar el texto del Label lblUser
    public void setUserName(String nombre) {
        lblUser.setText(nombre);
    }
    
    /**
     *
     */
    @FXML
    public void cerrarAplicacion() {
        Platform.exit();  // Cierra la aplicación
    }
}