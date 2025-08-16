/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Apoyo Docente
 */
public class UsuarioController {

    /*PreparedStatement pst = null;
    ResultSet rs = null;*/

    public UsuarioController() {

    }

    public DefaultTableModel TablaUsuarios() {

        String[] columnas = {"N°", "Nombre", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT id, nombre, correo FROM usuario;";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("correo");  
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
}
