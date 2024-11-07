package vistas;

import controlador.Cliente;
import controlador.SignableFactory;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import libreria.Request;
import libreria.Signable;
import libreria.Stream;
import libreria.User;

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
        // Verificar que todos los campos obligatorios están llenos
        if (idNombreApellido.getText().trim().isEmpty() || idDireccion.getText().trim().isEmpty()
                || idCiudad.getText().trim().isEmpty() || idCodigoPostal.getText().trim().isEmpty()
                || idCorreoElectronico.getText().trim().isEmpty() || idContrasena.getText().trim().isEmpty()
                || idRepetirContrasena.getText().trim().isEmpty()) {
            showAlert("Error", "Completa todos los campos");
            return;
        }

        // Variables para almacenar las contraseñas que se usarán en la verificación
        String password;
        String repeatPassword;

        // Comprobar cuál de los campos de contraseña está visible y tomar su valor
        if (idMostrarContrasena.isSelected()) {
            password = idContrasenaTextField.getText();
        } else {
            password = idContrasena.getText();
        }

        if (idMostrarRepetirContrasena.isSelected()) {
            repeatPassword = idRepetirContrasenaTextField.getText();
        } else {
            repeatPassword = idRepetirContrasena.getText();
        }

        // Verificar si las contraseñas coinciden
        if (!password.equals(repeatPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }

        // Validación de formato del correo electrónico
        String email = idCorreoElectronico.getText();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        if (!Pattern.matches(emailRegex, email)) {
            showAlert("Error", "Gmail no válido. Asegúrate de usar una dirección de correo de Gmail.");
            return;
        }

        // Crear el objeto User con los datos ingresados
        User user = new User();
        user.setNombreApellidos(idNombreApellido.getText());
        user.setDireccion(idDireccion.getText());
        user.setCiudad(idCiudad.getText());
        user.setCodigoPostal(Integer.parseInt(idCodigoPostal.getText()));
        user.setLogin(email);
        user.setPassword(password);  // Usamos el password que ya se definió arriba

        Signable signable = SignableFactory.getSignable();
        Stream stream;
        try {
            stream = signable.signUp(user);
            if (stream.getMensaje().equals(Request.OK_SINGUP)) {
                showAlert("Éxito", "¡Registrado!");
                mainApp.mostrarLogin();
            } else if (stream.getMensaje().equals(Request.USUARIO_EXISTE_EXCEPCION)) {
                showAlert("ERROR", "El email introducido ya está registrado en la base de datos");
            } else if (stream.getMensaje().equals(Request.EXCEPCION_EN_CONEXIONES)) {
                showAlert("ERROR", "Ha ocurrido un error con las conexiones");
            } else if (stream.getMensaje().equals(Request.EXCEPCION_INTERNA)) {
                showAlert("ERROR", "Ha ocurrido un error interno desconocido");
            } else {
                showAlert("ERROR", "Servidor apagado");
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void aplicarFondo() {
        fxmlSignUp.setStyle("-fx-background-image: url('" + mainApp.getFondoActual() + "');");
    }

    @FXML
    private void cambiarFondoParis(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

    @FXML
    private void cambiarFondoSanFrancisco(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

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
        item1.setOnAction(this::cambiarFondoSanFrancisco);
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
