/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionBaseDatos;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Eyleen y Steven
 */
public class ConexionSQL {
     private Connection conectar = null;

    public Connection conexion() {
        try {
           Class.forName("com.mysql.jdbc.Driver");
           conectar = (Connection) DriverManager.getConnection("jdbc:mysql://163.178.107.10:3306/bdproyectoredes", "laboratorios", "UCRSA.118");
        } catch (ClassNotFoundException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al Conectarse"+"\n"+error,"Mensaje Error",JOptionPane.ERROR_MESSAGE);
        }
        return conectar;

    }
}
