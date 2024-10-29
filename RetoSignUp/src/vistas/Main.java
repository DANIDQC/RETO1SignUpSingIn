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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        mostrarLogin();
    }

    public void mostrarLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();

        // Obtenemos el controlador de la vista de login
        FXMLSignInController controller = loader.getController();
        controller.setMainApp(this); // Pasar la referencia de MainApp

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Login");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void mostrarSignUp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
        Parent root = loader.load();

        // Obtenemos el controlador de la vista de signup
        FXMLSignUpController controller = loader.getController();
        controller.setMainApp(this); // Pasar la referencia de MainApp

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Sign Up");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void mostrarSignOut(String username) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBienvenido.fxml"));
        Parent root = loader.load();

        // Obtenemos el controlador de la vista de bienvenida
        FXMLSignOutController controller = loader.getController();
        controller.setUserName(username); // Pasar el nombre del usuario

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));
        primaryStage.setTitle("Bienvenido");
        primaryStage.setOnCloseRequest(this::cerrarVentana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para manejar el evento de cierre de la ventana
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

    public static void main(String[] args) {
        launch(args);
    }
}
