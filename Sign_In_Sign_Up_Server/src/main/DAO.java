package main;

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
    private final String ID_PARTNER = "SELECT MAX(id) FROM res_partner";
    private final String SIGN_UP = "INSERT INTO res_users (company_id, login, password, active, partner_id, notification_type) values (1, ?, ?, ?, ?, 'email')";

    public DAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Stream signIn(User user) throws Exception {
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
                    user.setNombreApellidos(rs.getString("name"));
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
                connectionPool.releaseConnection(connection);
            }
        }
        Stream stream = new Stream(user, Request.EXCEPCION_INTERNA);
        return stream;
    }

    @Override
    public Stream signUp(User user) {
        Connection connection = null;

        try {
            // Obtener la conexión del pool
            connection = connectionPool.getConnection();

            // Preparar la consulta SQL
            PreparedStatement stmt = connection.prepareStatement(SIGN_PARTNER);
            stmt.setString(1, user.getNombreApellidos());
            stmt.setString(2, user.getDireccion());
            stmt.setString(3, user.getCiudad());
            stmt.setInt(4, user.getCodigoPostal());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PreparedStatement stmt1 = connection.prepareStatement(ID_PARTNER);
                ResultSet rs1 = stmt1.executeQuery();

                if (rs1.next()) {
                    int id_partner = rs1.getInt("id");
                    PreparedStatement stmt2 = connection.prepareStatement(SIGN_UP);
                    stmt2.setString(1, user.getLogin());
                    stmt2.setString(2, user.getPassword());
                    stmt2.setBoolean(3, user.isActive());
                    stmt2.setInt(4, id_partner);

                    ResultSet rs2 = stmt1.executeQuery();
                    Stream stream = new Stream(user, Request.OK_SINGUP);
                    return stream;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            Stream stream = new Stream(user, Request.EXCEPCION_EN_CONEXIONES);
            return stream;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        Stream stream = new Stream(user, Request.EXCEPCION_INTERNA);
        return stream;
    }
}
