package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final String SIGN_UP = "INSERT INTO res_users (company_id, login, password, active, partner_id) values (1, ?, ?, ?, ?)";

    public DAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User signIn(User usuario, Connection connection) throws Exception {
        try {
            // Obtener la conexión del pool
            connection = connectionPool.getConnection();

            // Preparar la consulta SQL
            PreparedStatement stmt = connection.prepareStatement(SELECT_USER);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getPass());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_partner = rs.getInt("partner_id");
                PreparedStatement stmt1 = connection.prepareStatement(SELECT_PARTNER);
                stmt1.setInt(1, id_partner);
                
                ResultSet rs1 = stmt1.executeQuery();
                if (rs1.next()){
                    usuario.setNombre(rs.getString("name"));
                }
            }
        } catch (SQLException | InterruptedException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Liberar la conexión
            if (connection != null) {
                try {
                    connectionPool.releaseConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return usuario;
    }

    @Override
    public User signUp(User user, Connection connection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
