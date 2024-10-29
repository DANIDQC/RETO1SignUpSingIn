package vistas;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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
    @FXML
    private BorderPane fxmlSignUp;
    @FXML
    private boolean oscuro;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    // Método que se ejecuta cuando se presiona el botón de registro
    @FXML
    private void botonRegistrarseExitoso(ActionEvent event) {

        if (idNombreApellido.getText().trim().isEmpty() || idDireccion.getText().trim().isEmpty()
                || idCiudad.getText().trim().isEmpty() || idCodigoPostal.getText().trim().isEmpty()
                || idCorreoElectronico.getText().trim().isEmpty() || idContrasena.getText().trim().isEmpty()
                || idRepetirContrasena.getText().trim().isEmpty()) {
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
        showAlert("Éxito", "¡Registrado!");

    }

    // Método para cambiar el fondo de pantalla a la imagen de París
    public void cambiarFondoParis(ActionEvent event) {
        //Se obtiene el estilo del fondo.
        String estilo = fxmlSignUp.getStyle();

        //Se quita la imagen del fondo.
        String estiloNuevo = estilo.replace("-fx-background-image: url\\('.*'\\);", "");

        //Se añade al fondo la imagen con el tema oscuro
        fxmlSignUp.setStyle(estiloNuevo + "-fx-background-image: url('/img/fondoPantallaCambiado.jpg');");
        oscuro = true;
    }

    // Método para cambiar el fondo de pantalla a la imagen de San Francisco
    public void cambiarFondoSanFranciso(ActionEvent event) {
        //Se obtiene el estilo del fondo.
        String estilo = fxmlSignUp.getStyle();

        //Se quita la imagen del fondo.
        String estiloNuevo = estilo.replace("-fx-background-image: url\\('.*'\\);", "");

        //Se añade al fondo la imagen de San Francisco
        fxmlSignUp.setStyle(estiloNuevo + "-fx-background-image: url('/img/fondoPantalla1.png');");
        oscuro = true;
    }

    // Método para manejar la acción del botón de cerrar
    // Método para manejar la acción del botón de cerrar
    @FXML
    private void volverInicioSesion(ActionEvent event) {
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Deseas volver a la ventana de inicio de sesión?");

        // Mostrar la alerta y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, llama al método para mostrar la ventana de inicio de sesión
            if (mainApp != null) {
                try {
                    mainApp.mostrarLogin(); // Asegúrate de que este método esté definido en Main
                } catch (Exception ex) {
                    Logger.getLogger(FXMLSignUpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            // Si el usuario cancela, no se cierra la aplicación
            event.consume();  // Consumiendo el evento
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

        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Cambiar fondo de pantalla a San Francisco.");
        item1.setOnAction(this::cambiarFondoSanFranciso);
        MenuItem item2 = new MenuItem("Cambiar fondo de pantalla a Paris.");
        item2.setOnAction(this::cambiarFondoParis);
        menu.getItems().addAll(item1, item2);
        fxmlSignUp.setOnMouseClicked(event -> controlMenu(event, menu));
    }

    // Mostrar alertas de error o éxito
    @FXML
    void showAlert(String title, String message) {
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
        switch (mensaje) {
            case "OK_SIGNUP":
                //Vaya a la ventana de SignIn
                break;
            case "EXCEPCION_INTERNA":
                //Alert de expcepcion interna
                break;
            case "USUARIO_EXISTE_EXCEPCION":
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
            menu.show(fxmlSignUp, event.getScreenX(), event.getScreenY());
        } else {
            // Oculta el menú si se hace clic con otro botón
            menu.hide();
        }
    }
}
