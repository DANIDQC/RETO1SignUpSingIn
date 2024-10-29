package vistas;

import java.util.Optional;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class FXMLSignUpController {


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
    @FXML
    private CheckBox idActivo;

    // Método que se ejecuta cuando se presiona el botón de registro
    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (idNombreApellido.getText().trim().isEmpty() || idDireccion.getText().isEmpty()
                || idCiudad.getText().isEmpty() || idCodigoPostal.getText().isEmpty()
                || idCorreoElectronico.getText().isEmpty() || idContrasena.getText().isEmpty()
                || idRepetirContrasena.getText().isEmpty()) {
            showAlert("Error", "Completa todos los campos");
            return;
        }

        if (!idContrasena.getText().trim().equals(idRepetirContrasena.getText().trim()) || !idContrasenaTextField.getText().trim().equals(idRepetirContrasenaTextField.getText().trim())) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }
        String email = idCorreoElectronico.getText();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        if (!Pattern.matches(emailRegex, email)) {
            showAlert("Error", "Gmail no válido. Asegúrate de usar una dirección de correo de Gmail.");
            return;
        }

//        User usuario=new User();
//        usuario.getUsuario();
//        usuario.setContraseina();
//        usuario.setDni();
//        usuario.setNombre();
//        usuario.setApellido();
//        usuario.setEdad();
        showAlert("Éxito", "¡Registrado!");

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

        // Marcar el checkbox "Activo" como seleccionado por defecto
        idActivo.setSelected(true);
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
    
    
    public static void actualizarInterfazConMensaje(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
