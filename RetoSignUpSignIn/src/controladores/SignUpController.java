package controladores;

import dbConexion.DAO;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author 2dam
 */
public class SignUpController {

    // Método para crear un nuevo usuario (en este caso retorna un objeto User vacío)
    public User getUser() {
        return new User();
    }

    // Método para registrar el usuario y su partner
    public void SignUp(User usuario) throws SQLException {
        
            DAO dao = new DAO(); // Crear una instancia de DAO
            try {
                dao.conectar(); // Establecer conexión con la base de datos
                dao.RegistrarPartner(usuario); // Registrar Partner
                dao.RegistrarUsuario(usuario); // Registrar Usuario
                System.out.println("Registro exitoso.");
            } catch (SQLException e) {
                System.err.println("Error al registrar el usuario: " + e.getMessage());
                // Aquí podrías capturar la excepción y mostrar un mensaje al usuario, o re-lanzar la excepción
                throw e;
            } finally {
                dao.desconectar(); // Asegurarse de cerrar la conexión después de la operación
            }
        
    }
}
