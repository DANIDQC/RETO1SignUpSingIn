package retoSignUpSignIn;

import controladores.BienvenidaController;
import controladores.SignUpController;
import dbConexion.DAO;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import javafx.application.Application;
import model.User;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DAO dbConnection = new DAO();

        try {
            // Conectar a la base de datos
            dbConnection.conectar();
            
            // Ejecutar consulta y mostrar resultados
            dbConnection.mostrarUsuarios();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar la conexión
                dbConnection.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Verifica si el archivo FXML se encuentra
        URL fxmlUrl = getClass().getResource("/interfaces/PantallaBienvenida.fxml");
        if (fxmlUrl == null) {
            System.out.println("El archivo FXML no se ha encontrado.");
            return;
        }

        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        // Obtener el controlador vinculado al FXML
        BienvenidaController controller = loader.getController();
        String username="Josemiguel@gmail.com";
        // Establecer el nombre del usuario en el Label lblUser
        controller.setUserName(username);
        // Crear y mostrar la escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
