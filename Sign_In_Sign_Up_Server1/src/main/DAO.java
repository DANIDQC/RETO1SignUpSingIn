package main;

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
import org.postgresql.util.PSQLException;

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

    public DAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public synchronized Stream signIn(User user) throws ExcepcionLoginError, ExcepcionConexionesError {
        Connection connection = null;

        try {
            // Obtener la conexión del pool
            connection = connectionPool.getConnection();

            // Preparar la consulta SQL
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
                    Stream stream = new Stream(user, Request.OK_SINGIN);
                    return stream;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            Stream stream = new Stream(user, Request.LOG_IN_EXCEPCION);
            return stream;
        } finally {
            if (connection != null) {
                try {
                    connectionPool.returnConnection(connection);
                } catch (SQLException ex) {
                    throw new ExcepcionConexionesError();
                }
            }
        }
        Stream stream = new Stream(user, Request.EXCEPCION_INTERNA);
        return stream;
    }

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
            // Obtener la conexión del pool
            connection = connectionPool.getConnection();

            stmtEmail = connection.prepareStatement(SELECT_EMAIL);
            stmtEmail.setString(1, user.getLogin());

            rsEmail = stmtEmail.executeQuery();
            if (rsEmail.next()) {
                connection.close();
                throw new ExcepcionUsuarioExisteError();
            }

            // Insertar en la tabla de Partner
            stmtPartner = connection.prepareStatement(SIGN_PARTNER);
            stmtPartner.setString(1, user.getNombreApellidos());
            stmtPartner.setString(2, user.getDireccion());
            stmtPartner.setString(3, user.getCiudad());
            stmtPartner.setInt(4, user.getCodigoPostal());
            stmtPartner.executeUpdate();

            // Obtener el id_partner recién insertado
            stmtIdPartner = connection.prepareStatement(ID_PARTNER);
            rsIdPartner = stmtIdPartner.executeQuery();

            if (rsIdPartner.next()) {
                int id_partner = rsIdPartner.getInt("id");

                // Insertar en la tabla de Usuarios con el id_partner
                stmtSignUp = connection.prepareStatement(SIGN_UP);
                stmtSignUp.setString(1, user.getLogin());
                stmtSignUp.setString(2, user.getPassword());
                stmtSignUp.setBoolean(3, user.isActive());
                stmtSignUp.setInt(4, id_partner);
                stmtSignUp.executeUpdate();

                // Si se llega aquí, el registro fue exitoso
                return new Stream(user, Request.OK_SINGUP);
            } else {
                // No se encontró el id_partner, manejar error
                return new Stream(user, Request.EXCEPCION_INTERNA);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Error en signUp", ex);
            return new Stream(user, Request.EXCEPCION_EN_CONEXIONES);
        } finally {
            // Cerrar recursos en orden
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

            // Devolver la conexión al pool
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
