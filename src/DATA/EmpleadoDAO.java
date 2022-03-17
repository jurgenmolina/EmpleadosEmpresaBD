/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jurgenmolina <jurgenmolina29@gmail.com>
 */
public class EmpleadoDAO {

    public static final String SQL_GET_ALL = "SELECT * FROM empleado";
    private static final String SQL_CREATE = "INSERT INTO `empleado` (`id`, `nombre`, `dia`, `hora`) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `empleado` SET `nombre`=?, `dia`=?,`hora`=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM `empleado` WHERE id=?";

    public List<Empleado> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Empleado objeto;
        List<Empleado> lista = new ArrayList<>();

        try {
            con = ConexionBD.getConnection();
            ps = con.prepareStatement(this.SQL_GET_ALL);
            res = ps.executeQuery();
            while (res.next()) {

                objeto = new Empleado(
                        res.getInt("id"),
                        res.getString("nombre"),
                        res.getString("dia"),
                        res.getString("hora")
                );

                lista.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ConexionBD.close(res);
                ConexionBD.close(ps);
                ConexionBD.close(con);
            } catch (SQLException ex) {
                Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }

    public int create(Empleado e) {
        return this.ejecutarSQL(e, 3);
    }

    public int update(Empleado e) {
        return this.ejecutarSQL(e, 2);
    }

    public int delete(Empleado e) {
        return this.ejecutarSQL(e, 1);
    }

    private int ejecutarSQL(Empleado e, int t) {
        Connection con = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            con = ConexionBD.getConnection();
            switch (t) {
                case 1: {
                    ps = con.prepareStatement(this.SQL_DELETE);
                    ps.setInt(1, e.getId());
                    break;
                }
                case 2: {
                    ps = con.prepareStatement(this.SQL_UPDATE);
                    ps.setString(1, e.getNombre());
                    ps.setString(2, e.getDia());
                    ps.setString(3, e.getHora());
                    ps.setInt(4, e.getId());
                    break;
                }
                case 3: {
                    ps = con.prepareStatement(this.SQL_CREATE);
                    ps.setInt(1, e.getId());
                    ps.setString(2, e.getNombre());
                    ps.setString(3, e.getDia());
                    ps.setString(4, e.getHora());

                    break;
                }
                default:
                    break;
            }

            registros = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return registros;
    }
}
