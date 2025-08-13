/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class ProveedorController {
    
    public ProveedorController(){
        
    }
    
    public DefaultTableModel proveedorTabla(){
        
        String[] columnas = {"N°", "Nombre", "Telefono", "Email","Direccion"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT id, nombre,telefono, email,direccion FROM proveedores";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("email");
                fila[4] = rs.getString("direccion");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
}
