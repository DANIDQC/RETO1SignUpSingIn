package vistas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainBienvenido extends Application {

    private String username;

    // Constructor por defecto requerido por JavaFX
    public MainBienvenido() {
        // Inicializar sin nombre de usuario
        this.username = "Usuario Desconocido";
    }

    // Constructor que permite pasar el nombre de usuario
    public MainBienvenido(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/FXMLBienvenido.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la ventana de bienvenida
        BienvenidoController controller = loader.getController();
        // Pasar el nombre del usuario al controlador
        controller.setUserName(username);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoEquipo.PNG")));

        // Crear y mostrar la escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
