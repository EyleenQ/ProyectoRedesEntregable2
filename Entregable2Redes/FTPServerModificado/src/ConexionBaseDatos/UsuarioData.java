/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionBaseDatos;

import Domain.Usuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Eyleen
 */
public class UsuarioData {
    ResultSet res;

    public UsuarioData() {

    }

    public void insertarUsuario(Usuario u) {
        ConexionSQL con = new ConexionSQL();
        Connection registro = con.conexion();
        String sql = "{ call insertarUsuario(?,?) }";
        try {
            PreparedStatement pst = (PreparedStatement) registro.prepareStatement(sql);
            pst.setString(1, u.getNombreUsuario());
            pst.setString(2, u.getContrasenna());

            pst.executeQuery();
            pst.close();

        } catch (SQLException ex) {
        }catch (NullPointerException nullE){}
    }

    public ArrayList<Usuario> extraer() throws SQLException {
        ConexionSQL con = new ConexionSQL();
        Connection registro = con.conexion();
        String sql = "{ call obtenerUsuarios() }";
        PreparedStatement s = (PreparedStatement) registro.prepareStatement(sql);
        res = s.executeQuery(sql);
        ArrayList<Usuario> personasSQL = new ArrayList<Usuario>();
        try {
            while (res.next()) {
                Usuario u = new Usuario(res.getString(1),res.getString(2));
                personasSQL.add(u);
            }
        } catch (SQLException ex) {
        }catch (NullPointerException nullE){}
        res.close();
        s.close();
        registro.close();

        return personasSQL;
    }

    public Usuario extraerUsuario(String nombreUsuario) throws SQLException {
        ConexionSQL con = new ConexionSQL();
        Connection registro = con.conexion();
        String sql = "{ call obtenerUsuario(?) }";
        Usuario u = new Usuario();
        try {
            PreparedStatement pst = (PreparedStatement) registro.prepareStatement(sql);
            pst.setString(1, nombreUsuario);
                       
            res = pst.executeQuery();
            
            while (res.next()) {
                u.setNombreUsuario(res.getString(2));
                u.setContrasenna(res.getString(3));
            }
                            
            res.close();
            pst.close();
            registro.close();
        } catch (SQLException ex) {
        }catch (NullPointerException nullE){}
        
        return u;
    }
    
//    public void actualizar(Usuario u) {
//        ConexionSQL con = new ConexionSQL();
//        Connection registro = con.conexion();
//        String sql = "{ call actualizarUsuario(?,?) }";
//
//        try {
//            PreparedStatement pst = (PreparedStatement) registro.prepareStatement(sql);
//            pst.setString(1, u.getNombreUsuario());
//            pst.setString(2, u.getContrasenna());
//
//            pst.executeQuery();
//            pst.close();
//            registro.close();
//
//        } catch (SQLException ex) {
//        }catch (NullPointerException nullE){}
//    }
//    
//    public void eliminar(Usuario u) throws SQLException {
//        ConexionSQL con = new ConexionSQL();
//        Connection registro = con.conexion();
//        String sql = "{ call eliminarUsuario (?) }";
//
//        PreparedStatement pst = (PreparedStatement) registro.prepareStatement(sql);
//        pst.setString(1, u.getNombreUsuario());
//
//        pst.executeQuery();
//        pst.close();
//        registro.close();
//    }
}
