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
public class CategoriaController {
    
    public CategoriaController(){
        
    }
    
    public DefaultTableModel TablaCategoria(){
        
        String[] columnas = {"N°", "Nombre"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT id, nombre FROM categoria;";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
    
    public boolean insertarCategoria(String nombre){
        
        String sql = " INSERT INTO categoria (nombre) VALUES (?);";
        
        try(Connection conn = ConexionBD.conectar();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nombre);
           
           int filasafectadas = pst.executeUpdate();
           if(filasafectadas > 0 ){
            JOptionPane.showMessageDialog(null, "Categoria Registrada Exitosamente");
            return true;
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Categoria no se Registrado Exitosamente");
            return false;
        }
            return false;
    }
}
