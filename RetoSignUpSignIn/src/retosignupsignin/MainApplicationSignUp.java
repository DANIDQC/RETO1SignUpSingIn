package retosignupsignin;

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

public class MainApplicationSignUp extends Application {

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

        // Obtener el controlador vinculado al FXML
        SignUpController controller2 = new SignUpController();
        String username = "MariaFuente@gmail.com";
        User usuario = new User();
        usuario.setUsuario("Josemiguel@gmail.com");
        usuario.setContraseina("abcd*1234");
        usuario.setDni("12345678B");
        usuario.setNombre("Jose");
        usuario.setApellido("Miguel");
        usuario.setEdad(18);
      
        // Establecer el nombre del usuario en el Label lblUser
        controller2.SignUp(usuario);
        // Crear y mostrar la escena
    }

    public static void main(String[] args) {
        launch(args);
    }
}
