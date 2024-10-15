package retoSignUpSignIn;

import controladores.BienvenidaController;
import dbConexion.OdooJDBCConnection;
import java.sql.SQLException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import javafx.application.Application;

public class MainApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        OdooJDBCConnection dbConnection = new OdooJDBCConnection();

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
        
        // Nombre del usuario que quieres mostrar
        String nombre = "Josemiguel";

        // Verifica si el archivo FXML se encuentra
        URL fxmlUrl = getClass().getResource("/interfaces/PantallaBienvenida.fxml");
        if (fxmlUrl == null) {
            System.out.println("El archivo FXML no se ha encontrado.");
        } else {
            System.out.println("El archivo FXML se ha encontrado correctamente.");
        }

        // Carga el archivo FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        // Obtén el controlador vinculado al FXML
        BienvenidaController controller = loader.getController();

        // Establece el nombre del usuario en el Label lblUser
        controller.setUserName(nombre);

        // Crea y muestra la escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
