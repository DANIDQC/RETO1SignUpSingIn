package vistas;

import controlador.Cliente;
import controlador.SignableFactory;
import static java.awt.Color.red;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import libreria.Request;
import libreria.Signable;
import libreria.Stream;
import libreria.User;

/**
 * Controlador de la interfaz de usuario para la pantalla de registro (SignUp).
 * Esta clase maneja la interacción con el usuario en el proceso de registro de
 * nuevos usuarios. Incluye la validación de entradas, el manejo de eventos de
 * la interfaz de usuario y la comunicación con el servidor para registrar un
 * nuevo usuario.
 *
 * @author Andoni Garcia , Asier Del Campo
 */
public class FXMLSignUpController {

    // Atributos de la interfaz gráfica (FXML)
    /**
     * Campo de texto donde el usuario ingresa su nombre y apellido.
     */
    @FXML
    private TextField idNombreApellido;

    /**
     * Campo de texto donde el usuario ingresa su dirección.
     */
    @FXML
    private TextField idDireccion;

    /**
     * Campo de texto donde el usuario ingresa la ciudad de residencia.
     */
    @FXML
    private TextField idCiudad;

    /**
     * Campo de texto donde el usuario ingresa su código postal.
     */
    @FXML
    private TextField idCodigoPostal;

    /**
     * Campo de texto donde el usuario ingresa su correo electrónico.
     */
    @FXML
    private TextField idCorreoElectronico;

    /**
     * Campo de texto donde el usuario ingresa su contraseña.
     */
    @FXML
    private PasswordField idContrasena;

    /**
     * Campo de texto donde el usuario repite su contraseña para confirmar.
     */
    @FXML
    private PasswordField idRepetirContrasena;

    /**
     * Casilla de verificación que permite al usuario mostrar u ocultar la
     * contraseña.
     */
    @FXML
    private CheckBox idMostrarContrasena;

    /**
     * Casilla de verificación que permite al usuario mostrar u ocultar la
     * repetición de la contraseña.
     */
    @FXML
    private CheckBox idMostrarRepetirContrasena;

    /**
     * Botón de registro que el usuario presiona para completar el proceso de
     * registro.
     */
    @FXML
    private Button btnRegistrarse;

    /**
     * Campo de texto visible cuando el usuario elige mostrar la repetición de
     * la contraseña.
     */
    @FXML
    private TextField idRepetirContrasenaTextField;

    /**
     * Campo de texto visible cuando el usuario elige mostrar la contraseña.
     */
    @FXML
    private TextField idContrasenaTextField;

    /**
     * Casilla de verificación que indica si el usuario está activo.
     */
    @FXML
    private CheckBox idActivo;
    /**
     * Contenedor principal de la pantalla de registro, que maneja el diseño de
     * la interfaz.
     */
    @FXML
    private BorderPane fxmlSignUp;

    @FXML
    private Button idBotonOjo;

    @FXML
    private Button idBotonRepetirOjo;

    @FXML
    private Button btnLimpiar;

    /**
     * Instancia de la clase principal de la aplicación, utilizada para manejar
     * la navegación entre pantallas.
     */
    private Main mainApp;

    /**
     * Asigna la aplicación principal al controlador.
     *
     * @param mainApp La instancia de la clase principal de la aplicación.
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Método que se ejecuta cuando el usuario presiona el botón de registro.
     * Realiza varias validaciones sobre los campos de entrada y, si todo es
     * correcto, intenta registrar al nuevo usuario mediante la interacción con
     * el servidor.
     *
     * @param event El evento generado por la acción de presionar el botón.
     */
    @FXML
    private void botonRegistrarseExitoso(ActionEvent event) {
        // Validación de campos obligatorios
        if (idNombreApellido.getText().trim().isEmpty() || idDireccion.getText().trim().isEmpty()
                || idCiudad.getText().trim().isEmpty() || idCodigoPostal.getText().trim().isEmpty()
                || idCorreoElectronico.getText().trim().isEmpty() || idContrasena.getText().trim().isEmpty()
                || idRepetirContrasena.getText().trim().isEmpty()) {
            showAlert("Error", "Completa todos los campos");
            return;
        }

        // Verificación de las contraseñas
        String password;
        String repeatPassword;

        if (idContrasenaTextField.isVisible()) {
            password = idContrasenaTextField.getText();
        } else {
            password = idContrasena.getText();
        }

        if (idRepetirContrasenaTextField.isVisible()) {
            repeatPassword = idRepetirContrasenaTextField.getText();
        } else {
            repeatPassword = idRepetirContrasena.getText();
        }

        // Verificar si las contraseñas coinciden
        if (!password.equals(repeatPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }

        // Validación de correo electrónico
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
        user.setPassword(password);

        // Interactuar con el servidor para registrar al usuario
        Signable signable = SignableFactory.getSignable();
        Stream stream;
        try {
            stream = signable.signUp(user);
            // Dependiendo de la respuesta del servidor, mostrar el resultado correspondiente
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

    /**
     * Método que aplica el fondo de pantalla actual.
     */
    public void aplicarFondo() {
        fxmlSignUp.setStyle("-fx-background-image: url('" + mainApp.getFondoActual() + "');");
    }

    /**
     * Método para cambiar el fondo de la interfaz a una imagen de París.
     *
     * @param event El evento generado por la acción de cambiar el fondo.
     */
    @FXML
    private void cambiarFondoParis(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

    /**
     * Método para cambiar el fondo de la interfaz a una imagen de San
     * Francisco.
     *
     * @param event El evento generado por la acción de cambiar el fondo.
     */
    @FXML
    private void cambiarFondoSanFrancisco(ActionEvent event) {
        mainApp.cambiarFondo();
        aplicarFondo();
    }

    /**
     * Método que maneja la acción de volver a la ventana de inicio de sesión.
     * Muestra una alerta de confirmación antes de permitir al usuario salir de
     * la pantalla de registro.
     *
     * @param event El evento generado por la acción de presionar el botón de
     * volver.
     */
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
            // Si el usuario confirma, mostrar la ventana de inicio de sesión
            if (mainApp != null) {
                try {
                    mainApp.mostrarLogin();
                } catch (Exception ex) {
                    Logger.getLogger(FXMLSignUpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            event.consume();  // Cancelar la acción si el usuario no confirma
        }
    }

    /**
     * Método de inicialización que configura los controles de la interfaz.
     * Incluye la instalación de tooltips y validaciones de entrada para los
     * campos de texto.
     */
    @FXML
    public void initialize() {
        Tooltip tooltip = new Tooltip("Solo se pueden introducir números");
        Tooltip.install(idCodigoPostal, tooltip);
        idCodigoPostal.setOnKeyTyped(event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                idCodigoPostal.setStyle("-fx-border-color: red");
                event.consume();
            } else {
                idCodigoPostal.setStyle("-fx-border-color: white");
            }
        });

        Tooltip tooltipNombreApellido = new Tooltip("Solo se pueden introducir letras");
        Tooltip.install(idNombreApellido, tooltipNombreApellido);

        idNombreApellido.setOnKeyTyped(event -> {
            if (!event.getCharacter().matches("[a-zA-Zá-úÁ-Ú\\s]")) {
                event.consume();
            }
        });

        // Verificación de las contraseñas
        String password = idContrasena.getText();
        String repeatPassword = idRepetirContrasena.getText();

        if (!password.equals(repeatPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }

        // Marcar el checkbox "Activo" como seleccionado por defecto
        idActivo.setSelected(true);

        // Configurar el menú contextual para cambiar el fondo de pantalla
        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Cambiar fondo de pantalla a San Francisco.");
        item1.setOnAction(this::cambiarFondoSanFrancisco);
        MenuItem item2 = new MenuItem("Cambiar fondo de pantalla a Paris.");
        item2.setOnAction(this::cambiarFondoParis);
        menu.getItems().addAll(item1, item2);
        fxmlSignUp.setOnMouseClicked(event -> controlMenu(event, menu));

        idBotonOjo.setText("u");
        idBotonRepetirOjo.setText("u");

    }

    /**
     * Muestra una alerta con el título y mensaje proporcionados.
     *
     * @param title El título de la alerta.
     * @param message El mensaje que se mostrará en la alerta.
     */
    @FXML
    void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra u oculta la repetición de la contraseña dependiendo de si el
     * checkbox de mostrar la contraseña está marcado.
     *
     * @param event El evento generado por la acción de presionar el botón de
     * ver/ocultar la repetición de la contraseña.
     */
    @FXML
    private void verContraRepe(ActionEvent event) {
        if (!idRepetirContrasenaTextField.isVisible()) {
            idRepetirContrasenaTextField.setText(idRepetirContrasena.getText());
            idRepetirContrasenaTextField.setVisible(true);
            idRepetirContrasenaTextField.setManaged(true);
            idRepetirContrasena.setVisible(false);
            idRepetirContrasena.setManaged(false);
            idBotonRepetirOjo.setText("o");
        } else {
            idRepetirContrasena.setText(idRepetirContrasenaTextField.getText());
            idRepetirContrasena.setVisible(true);
            idRepetirContrasena.setManaged(true);
            idRepetirContrasenaTextField.setVisible(false);
            idRepetirContrasenaTextField.setManaged(false);
            idBotonRepetirOjo.setStyle("u");
        }
    }

    /**
     * Muestra u oculta la contraseña dependiendo de si el checkbox de mostrar
     * la contraseña está marcado.
     *
     * @param event El evento generado por la acción de presionar el botón de
     * ver/ocultar la contraseña.
     */
    @FXML
    private void verContra(ActionEvent event) {
        if (!idContrasenaTextField.isVisible()) {
            idContrasenaTextField.setText(idContrasena.getText());
            idContrasenaTextField.setVisible(true);
            idContrasenaTextField.setManaged(true);
            idContrasena.setVisible(false);
            idContrasena.setManaged(false);
            idBotonOjo.setText("o");

        } else {
            idContrasena.setText(idContrasenaTextField.getText());
            idContrasena.setVisible(true);
            idContrasena.setManaged(true);
            idContrasenaTextField.setVisible(false);
            idContrasenaTextField.setManaged(false);
            idBotonOjo.setText("u");
        }
    }

    /**
     * Controla el comportamiento del menú contextual en función del tipo de
     * clic.
     *
     * @param event El evento de clic del ratón.
     * @param menu El menú contextual que se debe mostrar u ocultar.
     */
    private void controlMenu(MouseEvent event, ContextMenu menu) {
        if (event.getButton() == MouseButton.SECONDARY) {
            menu.show(fxmlSignUp, event.getScreenX(), event.getScreenY());
        } else {
            menu.hide();
        }
    }

    @FXML
    private void limpiarDatos(ActionEvent event) {
        idNombreApellido.setText("");
        idDireccion.setText("");
        idCiudad.setText("");
        idCodigoPostal.setText("");
        idCorreoElectronico.setText("");
        idContrasena.setText("");
        idRepetirContrasena.setText("");
        idContrasenaTextField.setText("");
        idRepetirContrasenaTextField.setText("");
        idActivo.setSelected(true);
    }

    @FXML
    private void confimarDesactivo(ActionEvent event) {
        if (!idActivo.isSelected()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro?");
            alert.setContentText("¿Deseas desmarcar el checkbox?");

            ButtonType buttonAccept = new ButtonType("Aceptar");
            ButtonType buttonCancel = new ButtonType("Cancelar");

            alert.getButtonTypes().setAll(buttonAccept, buttonCancel);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonAccept) {
                    idActivo.setSelected(false);
                } else {
                    idActivo.setSelected(true);
                }
            });
        }
    }
}
