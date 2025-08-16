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
        
        String[] columnas = {"N°", "Nombre", "Telefono","Direccion"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT id, nombre,telefono,direccion FROM proveedor";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("direccion");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
    public boolean insertarProveedor(String nombre, String telefono, String direccion){
        
        String sql = " INSERT INTO proveedor (nombre, telefono, direccion) VALUES (?, ?, ?);";
        
        try(Connection conn = ConexionBD.conectar();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, direccion);
           
           int filasafectadas = pst.executeUpdate();
           if(filasafectadas > 0 ){
            JOptionPane.showMessageDialog(null, "Proveedor Registrado Exitosamente");
            return true;
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "proveedor No se Registrado Exitosamente");
            return false;
        }
            return false;
    }
}
