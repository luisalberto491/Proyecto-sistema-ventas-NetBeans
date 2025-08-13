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
public class VentasController {
    
    
    public VentasController(){

    }
    
    public DefaultTableModel TablaVentas(){
       
       String[] columnas = {"N°","Usuario","Total", "Fecha"};
       DefaultTableModel modelo = new DefaultTableModel(null, columnas); 
       
       String sql = """
                    SELECT 
                        ventas.id, 
                        usuarios.nombre,
                        ventas.total,
                        ventas.fecha
                    FROM ventas
                    INNER JOIN usuarios ON ventas.usuario_id = usuarios.id;"""; 
       
        try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            ) {          
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getFloat("total");
                fila[3] = rs.getDate("fecha");  
                modelo.addRow(fila);
            }
            
        } catch (Exception e) {
        }
       return modelo;
    }
    
    public DefaultTableModel TablaDetalleVentas(){
        
        String[] columnas = {"N°","IDventa","Producto", "Cantidad", "Precio Unitario", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = """
                     SELECT 
                            detalle_ventas.id, 
                            detalle_ventas.venta_id,
                            productos.nombre, 
                            detalle_ventas.cantidad, 
                            detalle_ventas.precio_unitario,
                            detalle_ventas.subtotal
                     FROM detalle_ventas
                     INNER JOIN productos ON detalle_ventas.producto_id = productos.id""";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
           
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("venta_id");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getInt("cantidad");
                fila[4] = rs.getFloat("precio_unitario");  
                fila[5] = rs.getFloat("subtotal");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
    
    public float totalventas(){
        
        String sql ="select sum(total) as total from ventas;";
        float totalventas = 0;
        try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();   
            ){
            
            rs.next();
            totalventas = rs.getFloat("total");
            
        } catch (Exception e) {
        }
        
        return totalventas;
    }
    
  public void RegistrarVentas(String producto,float precio, int cantidad,float total,String detalle){
      
     String sqlVenta = "INSERT INTO ventas (usuario_id, total, fecha) VALUES (1, 3.55, NOW());";
     String sqlDetalle = "INSERT INTO detalle_ventas (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (1, 1, 2, 1.00, 2.00)";
  }
  
  public void RegistrarDetalleDeVenta(){
      
  }
}
