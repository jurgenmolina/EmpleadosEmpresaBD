/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DATA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;


/**
 * 
 * @author Jurgenmolina <jurgenmolina29@gmail.com>
 */

public class ConexionBD {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/horario";
    public static final String BD_USUARIO = "root";
    public static final String BD_CLAVE = "";
    
    
    public static DataSource getDataSource() {
        BasicDataSource bs = new BasicDataSource();
        bs.setUrl(JDBC_URL);
        bs.setUsername(BD_USUARIO);
        bs.setPassword(BD_CLAVE);
        bs.setInitialSize(50);

        return bs;
    }

    public static Connection getConnection() throws SQLException {
        try {
            // return DriverManager.getConnection(JDBC_URL, BD_USUARIO, BD_CLAVE);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getDataSource().getConnection();
    }
    
    public static void close(Connection con) throws SQLException{
        con.close();
    }

    public static void close(Statement state) throws SQLException{
        state.close();
    }

    public static void close(ResultSet res) throws SQLException{
        res.close();
    }

    public static void close(PreparedStatement ps) throws SQLException{
        ps.close();
    }


}
