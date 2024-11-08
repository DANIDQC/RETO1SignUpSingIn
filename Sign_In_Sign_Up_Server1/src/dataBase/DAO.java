package dataBase;

import excepciones.ExcepcionConexionesError;
import excepciones.ExcepcionInternaServidorError;
import excepciones.ExcepcionLoginError;
import excepciones.ExcepcionUsuarioExisteError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import libreria.Request;
import libreria.Signable;
import libreria.Stream;
import libreria.User;

/**
 * La clase DAO (Data Access Object) permite el acceso a la base de datos para realizar
 * operaciones de autenticación (sign-in) y registro de usuarios (sign-up).
 * Implementa la interfaz Signable para asegurar la compatibilidad con métodos de
 * autenticación y registro.
 *
 * Esta clase utiliza un pool de conexiones a la base de datos para mejorar la eficiencia
 * y gestión de recursos. También maneja excepciones personalizadas para indicar problemas
 * específicos, como errores de conexión, login fallido y duplicados de usuario.
 *
 * Consultas SQL utilizadas:
 * - SELECT_USER: Verifica si un usuario existe en la base de datos y coincide con la contraseña.
 * - SELECT_PARTNER: Obtiene información adicional de un usuario asociado (partner) mediante su ID.
 * - OK_USER: Verifica si un login ya existe en la base de datos para evitar duplicados.
 * - SIGN_PARTNER: Inserta un nuevo "partner" (usuario asociado) en la base de datos.
 * - ID_PARTNER: Obtiene el ID más reciente de un "partner" creado en la base de datos.
 * - SIGN_UP: Inserta un nuevo usuario en la base de datos y lo asocia a un "partner".
 * - SELECT_EMAIL: Verifica si un correo electrónico ya está registrado en la base de datos.
 * 
 * @author Asier del Campo, Andoni Garcia
 */
public class DAO implements Signable {

    private final Pool connectionPool;
    private Connection con;
    private PreparedStatement stmt;
    private PreparedStatement stmt1;
    private ResourceBundle fichConf;
    private ResultSet rs;
    private ResultSet rs1;
    private final String SELECT_USER = "SELECT * FROM res_users WHERE login = ? and password = ?";
    private final String SELECT_PARTNER = "SELECT * FROM res_partner WHERE id = ?";
    private final String OK_USER = "SELECT login FROM res_user WHERE login = ?";
    private final String SIGN_PARTNER = "INSERT INTO res_partner (company_id, name, street, city, zip) values (1, ?, ?, ?, ?)";
    private final String ID_PARTNER = "SELECT id FROM res_partner ORDER BY id desc limit 1";
    private final String SIGN_UP = "INSERT INTO res_users (company_id, login, password, active, partner_id, notification_type) values (1, ?, ?, ?, ?, 'email')";
    private final String SELECT_EMAIL = "SELECT login FROM res_users WHERE login = ?";

    /**
     * Constructor de la clase DAO.
     * 
     * @param connectionPool El pool de conexiones utilizado para gestionar las conexiones
     *                       a la base de datos
     */
    public DAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Realiza el proceso de inicio de sesión (sign-in) para un usuario.
     * Verifica las credenciales del usuario en la base de datos y devuelve
     * un Stream con la información del usuario si las credenciales son correctas.
     * 
     * @param user El objeto User que contiene las credenciales de inicio de sesión
     * @return Un Stream que indica el resultado del inicio de sesión, incluyendo
     *         el usuario y el estado de la solicitud
     * @throws ExcepcionLoginError Si las credenciales del usuario son incorrectas
     * @throws ExcepcionConexionesError Si ocurre un problema al gestionar la conexión
     */
    @Override
    public synchronized Stream signIn(User user) throws ExcepcionLoginError, ExcepcionConexionesError {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_USER);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_partner = rs.getInt("partner_id");
                PreparedStatement stmt1 = connection.prepareStatement(SELECT_PARTNER);
                stmt1.setInt(1, id_partner);

                ResultSet rs1 = stmt1.executeQuery();
                if (rs1.next()) {
                    user.setNombreApellidos(rs1.getString("name"));
                    return new Stream(user, Request.OK_SINGIN);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return new Stream(user, Request.LOG_IN_EXCEPCION);
        } finally {
            if (connection != null) {
                try {
                    connectionPool.returnConnection(connection);
                } catch (SQLException ex) {
                    throw new ExcepcionConexionesError();
                }
            }
        }
        return new Stream(user, Request.EXCEPCION_INTERNA);
    }

    /**
     * Realiza el proceso de registro (sign-up) para un nuevo usuario.
     * Inserta al usuario en la base de datos, incluyendo la creación de un partner
     * y la asociación del usuario a este partner. Verifica si el usuario ya existe
     * antes de realizar el registro.
     * 
     * @param user El objeto User que contiene los datos de registro del usuario
     * @return Un Stream que indica el resultado del registro, incluyendo el usuario
     *         y el estado de la solicitud
     * @throws ExcepcionConexionesError Si ocurre un problema al gestionar la conexión
     * @throws ExcepcionInternaServidorError Si ocurre un error interno durante el registro
     * @throws ExcepcionUsuarioExisteError Si el usuario ya existe en la base de datos
     */
    @Override
    public synchronized Stream signUp(User user) throws ExcepcionConexionesError, ExcepcionInternaServidorError, ExcepcionUsuarioExisteError {
        Connection connection = null;
        PreparedStatement stmtEmail = null;
        PreparedStatement stmtPartner = null;
        PreparedStatement stmtIdPartner = null;
        PreparedStatement stmtSignUp = null;
        ResultSet rsIdPartner = null;
        ResultSet rsEmail = null;

        try {
            connection = connectionPool.getConnection();

            stmtEmail = connection.prepareStatement(SELECT_EMAIL);
            stmtEmail.setString(1, user.getLogin());

            rsEmail = stmtEmail.executeQuery();
            if (rsEmail.next()) {
                connection.close();
                return new Stream(user, Request.USUARIO_EXISTE_EXCEPCION);
            }

            stmtPartner = connection.prepareStatement(SIGN_PARTNER);
            stmtPartner.setString(1, user.getNombreApellidos());
            stmtPartner.setString(2, user.getDireccion());
            stmtPartner.setString(3, user.getCiudad());
            stmtPartner.setInt(4, user.getCodigoPostal());
            stmtPartner.executeUpdate();

            stmtIdPartner = connection.prepareStatement(ID_PARTNER);
            rsIdPartner = stmtIdPartner.executeQuery();

            if (rsIdPartner.next()) {
                int id_partner = rsIdPartner.getInt("id");

                stmtSignUp = connection.prepareStatement(SIGN_UP);
                stmtSignUp.setString(1, user.getLogin());
                stmtSignUp.setString(2, user.getPassword());
                stmtSignUp.setBoolean(3, user.isActive());
                stmtSignUp.setInt(4, id_partner);
                stmtSignUp.executeUpdate();

                return new Stream(user, Request.OK_SINGUP);
            } else {
                return new Stream(user, Request.EXCEPCION_INTERNA);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Error en signUp", ex);
            return new Stream(user, Request.EXCEPCION_EN_CONEXIONES);
        } finally {
            try {
                if (rsIdPartner != null) {
                    rsIdPartner.close();
                }
                if (stmtPartner != null) {
                    stmtPartner.close();
                }
                if (stmtIdPartner != null) {
                    stmtIdPartner.close();
                }
                if (stmtSignUp != null) {
                    stmtSignUp.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", e);
            }

            if (connection != null) {
                try {
                    connectionPool.returnConnection(connection);
                } catch (SQLException ex) {
                    throw new ExcepcionConexionesError();
                }
            }
        }
    }
}

