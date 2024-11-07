/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase principal de la aplicación que extiende {@link Application}.
 * Administra la inicialización de la interfaz gráfica de usuario y la navegación 
 * entre las diferentes ventanas de la aplicación (Login, SignUp, SignOut).
 */
public class Main extends Application {

    /**
     * Ventana principal de la aplicación.
     */
    private Stage primaryStage;

    /**
     * Estado del fondo de pantalla actual. 
     * {@code true} representa el fondo inicial, mientras que {@code false} 
     * indica que se ha cambiado el fondo.
     */
    private boolean fondoPantalla = true;

    /**
     * Ruta de la imagen del fondo de pantalla actual.
     */
    private String fondoActual = "/img/fondoPantalla1.png";

    /**
     * Obtiene el estado del fondo de pantalla actual.
     * 
     * @return {@code true} si el fondo es el predeterminado; {@code false} si ha sido cambiado.
     */
    public boolean isFondoPantalla() {
        return fondoPantalla;
    }

    /**
     * Establece el estado del fondo de pantalla actual.
     * 
     * @param fondoPantalla {@code true} para el fondo predeterminado, {@code false} para el alternativo.
     */
    public void setFondoPantalla(boolean fondoPantalla) {
        this.fondoPantalla = fondoPantalla;
    }

    /**
     * Obtiene la ruta de la imagen del fondo de pantalla actual.
     * 
     * @return Ruta de la imagen de fondo.
     */
    public String getFondoActual() {
        return fondoActual;
    }

    /**
     * Establece una nueva ruta de fondo de pantalla.
     * 
     * @param fondoActual Ruta de la nueva imagen de fondo.
     */
    public void setFondoActual(String fondoActual) {
        this.fondoActual = fondoActual;
    }

    /**
     * Método de inicio de la aplicación que configura y muestra la ventana de inicio de sesión.
     * 
     * @param stage Escenario principal de la aplicación.
     * @throws Exception Si ocurre un error al cargar la vista de inicio de sesión.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        mostrarLogin();
    }

    /**
     * Muestra la ventana de inicio de sesión de la aplicación.
     * 
     * @throws Exception Si ocurre un error al cargar la vista de login.
     */
    public void mostrarLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();
        FXMLSignInController controller = loader.getController();
        controller.setMainApp(this);
        aplicarFondo(root);

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Login");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana de registro de usuario (Sign Up).
     * 
     * @throws Exception Si ocurre un error al cargar la vista de registro.
     */
    public void mostrarSignUp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
        Parent root = loader.load();
        FXMLSignUpController controller = loader.getController();
        controller.setMainApp(this);
        aplicarFondo(root);

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Sign Up");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la ventana de bienvenida (Sign Out) después de un inicio de sesión exitoso.
     * 
     * @param username Nombre del usuario que ha iniciado sesión.
     * @throws Exception Si ocurre un error al cargar la vista de bienvenida.
     */
    public void mostrarSignOut(String username) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBienvenido.fxml"));
        Parent root = loader.load();
        FXMLSignOutController controller = loader.getController();
        controller.setMainApp(this);
        controller.setUserName(username);
        aplicarFondo(root);

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Sign Out");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Aplica la imagen de fondo actual al contenedor de la vista.
     * 
     * @param root Nodo raíz de la escena a la que se aplicará el fondo.
     */
    private void aplicarFondo(Parent root) {
        root.setStyle("-fx-background-image: url('" + fondoActual + "');");
    }

    /**
     * Cambia el fondo de pantalla entre el fondo inicial y el alternativo.
     */
    public void cambiarFondo() {
        if (fondoPantalla) {
            setFondoActual("/img/fondoPantallaCambiado.jpg");
        } else {
            setFondoActual("/img/fondoPantalla1.png");
        }
        fondoPantalla = !fondoPantalla;
    }

    /**
     * Muestra una alerta de confirmación antes de cerrar la aplicación.
     * 
     * @param event Evento de cierre de la ventana.
     */
    private void cerrarVentana(WindowEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
     * Método principal de la aplicación que lanza la interfaz gráfica.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
