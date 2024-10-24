package controladores;

import dbConexion.DAO;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BienvenidaController {
    
    @FXML
    private Label lblUser;  // Vincula el Label desde el FXML

    // Método para cambiar el texto del Label lblUser
    public void setUserName(String username) {
        DAO dao = new DAO();  // Crear una instancia de DAO
        String nombre;

        try {
            dao.conectar();  // Asegurarse de abrir la conexión
            nombre = dao.buscarNombreporUsername(username);  // Llamar al método buscarNombreporUsername
            
            if (nombre != null && !nombre.isEmpty()) {
                lblUser.setText(nombre);  // Asignar el nombre al Label si es válido
            } else {
                lblUser.setText("Usuario no encontrado");  // Mensaje por defecto si no se encuentra el usuario
            }

        } catch (SQLException e) {
            lblUser.setText("Error al obtener nombre de usuario");  // Mensaje en caso de excepción
            e.printStackTrace();
        } finally {
            try {
                dao.desconectar();  // Cerrar la conexión
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cerrarAplicacion() {
        Platform.exit();  // Cierra la aplicación
    }
}
