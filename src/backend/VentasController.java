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
       
       String[] columnas = {"N°","Usuario","Fecha","Total"};
       DefaultTableModel modelo = new DefaultTableModel(null, columnas); 
       
       String sql = """
                    SELECT 
                      ventas.id, 
                      usuario.nombre,
                      ventas.fecha,
                      ventas.total
                    FROM ventas
                    INNER JOIN usuario ON ventas.id_usuario = usuario.id;"""; 
       
        try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            ) {          
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDate("fecha");
                fila[3] = rs.getFloat("total");  
                modelo.addRow(fila);
            }
            
        } catch (Exception e) {
        }
       return modelo;
    }
    
    public DefaultTableModel TablaDetalleVentas(){
        
        String[] columnas = {"N°","venta","Producto", "Cantidad", "Precio Unitario", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = """
                     SELECT 
                        detalle_venta.id, 
                        detalle_venta.id_venta,
                        producto.nombre, 
                        detalle_venta.cantidad, 
                        detalle_venta.precio_unitario,
                        detalle_venta.subtotal
                    FROM detalle_venta
                    INNER JOIN producto ON detalle_venta.id_producto = producto.id;""";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
           
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("id_venta");
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
    
  public boolean RegistrarVentas(String producto,double precio_unitario, int cantidad,double total_pagar){
     
     int id_usuario = Sesión.getId();
     String sql = "INSERT INTO ventas (id_usuario, fecha, total) VALUES (?,NOW(),?);";
     String sql2 = "SELECT id FROM ventas ORDER BY id DESC LIMIT 1;";
     String sql3 = "select id from producto where nombre = ?;";
      try (
            
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql); 
            // consulta para sacar id de ventas 
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            //Consulta para sacar id de producto  
            PreparedStatement ps3 = conn.prepareStatement(sql3);
           ){
                
           ps.setInt(1, id_usuario);
           ps.setDouble(2, total_pagar);
           
           ps3.setString(1, producto);          
           ResultSet rs3 = ps3.executeQuery();
           
           int filasafectadas = ps.executeUpdate();
           if(filasafectadas > 0 ){
               
               rs2.next();
               rs3.next();
               RegistrarDetalleDeVenta(rs2.getInt("id"), rs3.getInt("id"), cantidad, precio_unitario);
               JOptionPane.showMessageDialog(null, "Venta Registrada Exitosamente");
               return true;
           }
      } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "No se pudo Registrala Venta" + e);           
              return false;        
      }
      return false;
  }
  
  private void RegistrarDetalleDeVenta(int idventa, int idproducto,int cantidad, double precio_unitario){
    
    String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
      
    try (
         Connection conn = ConexionBD.conectar();    
         PreparedStatement ps = conn.prepareStatement(sql);   
         ){
          ps.setInt(1, idventa);
          ps.setInt(2, idproducto);
          ps.setInt(3, cantidad);
          ps.setDouble(4, precio_unitario);
         
         int filasafectadas = ps.executeUpdate();
         if(filasafectadas > 0){
            JOptionPane.showMessageDialog(null, "Se Registro el detalle de venta");          
            //return true;
         }               
      } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se Registro el detalle de venta" + e);          
            //return false;
      }
  }
}
