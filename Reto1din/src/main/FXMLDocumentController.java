package main;

import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class FXMLDocumentController {

    @FXML
    private TextField idNombreApellido;
    @FXML
    private TextField idDireccion;
    @FXML
    private TextField idCiudad;
    @FXML
    private TextField idCodigoPostal;
    @FXML
    private TextField idCorreoElectronico;
    @FXML
    private PasswordField idContrasena;
    @FXML
    private PasswordField idRepetirContrasena;
    @FXML
    private CheckBox idMostrarContrasena;
    @FXML
    private CheckBox idMostrarRepetirContrasena;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private TextField idRepetirContrasenaTextField;

    @FXML
    private TextField idContrasenaTextField;

    // Método que se ejecuta cuando se presiona el botón de registro
    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (idNombreApellido.getText().isEmpty() || idDireccion.getText().isEmpty()
                || idCiudad.getText().isEmpty() || idCodigoPostal.getText().isEmpty()
                || idCorreoElectronico.getText().isEmpty() || idContrasena.getText().isEmpty()
                || idRepetirContrasena.getText().isEmpty()) {
            showAlert("Error", "Completa todos los campos");
            return;
        }

        String password = idContrasena.getText();
        String repeatPassword = idRepetirContrasena.getText();

        if (!password.equals(repeatPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }
        String email = idCorreoElectronico.getText();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        if (!Pattern.matches(emailRegex, email)) {
            showAlert("Error", "Gmail no válido. Asegúrate de usar una dirección de correo de Gmail.");
            return;
        }

        showAlert("Éxito", "¡Registrado!");

    }

    // Inicialización de los componentes de la interfaz
    @FXML
    public void initialize() {
        Tooltip tooltip = new Tooltip("Solo se pueden introducir números");
        Tooltip.install(idCodigoPostal, tooltip);

        idCodigoPostal.setOnKeyTyped(event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });

        Tooltip tooltipNombreApellido = new Tooltip("Solo se pueden introducir letras");
        Tooltip.install(idNombreApellido, tooltipNombreApellido);

        idNombreApellido.setOnKeyTyped(event -> {
            if (!event.getCharacter().matches("[a-zA-Z\\s]")) {
                event.consume();
            }
        });

        String password = idContrasena.getText();
        String repeatPassword = idRepetirContrasena.getText();

        if (!password.equals(repeatPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }
    }

    // Mostrar alertas de error o éxito
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Mostrar/Ocultar la repetición de la contraseña
    @FXML
    private void verContraRepe(ActionEvent event) {
        if (idMostrarRepetirContrasena.isSelected()) {
            idRepetirContrasenaTextField.setText(idRepetirContrasena.getText());
            idRepetirContrasenaTextField.setVisible(true);
            idRepetirContrasenaTextField.setManaged(true);
            idRepetirContrasena.setVisible(false);
            idRepetirContrasena.setManaged(false);
        } else {
            idRepetirContrasena.setText(idRepetirContrasenaTextField.getText());
            idRepetirContrasena.setVisible(true);
            idRepetirContrasena.setManaged(true);
            idRepetirContrasenaTextField.setVisible(false);
            idRepetirContrasenaTextField.setManaged(false);
        }
    }

    @FXML
    private void verContra(ActionEvent event) {
        if (idMostrarContrasena.isSelected()) {
            idContrasenaTextField.setText(idContrasena.getText());
            idContrasenaTextField.setVisible(true);
            idContrasenaTextField.setManaged(true);
            idContrasena.setVisible(false);
            idContrasena.setManaged(false);
        } else {
            idContrasena.setText(idContrasenaTextField.getText());
            idContrasena.setVisible(true);
            idContrasena.setManaged(true);
            idContrasenaTextField.setVisible(false);
            idContrasenaTextField.setManaged(false);
        }
    }

}
