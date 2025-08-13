/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Apoyo Docente
 */
public class ProductosController {
 
    
   public ProductosController(){
       
   }
   public boolean insertarProducto(String nombre,String descripcion, float preciocompra, float presioventa, int stock, int categoria, int proveedor, String img){
    
       String sql = "INSERT INTO productos (nombre, descripcion, precio_compra, precio_venta, stock, categoria_id, proveedor_id)"
               + "   VALUES (?,?,?,?,?,?,?);";
       try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ){
           
           ps.setString(1, nombre);
           ps.setString(2, descripcion);
           ps.setFloat(3, preciocompra);
           ps.setFloat(4, presioventa);
           ps.setInt(5, stock);
           ps.setInt(6, categoria);
           ps.setInt(7, proveedor);
           //ps.setString(8, img);
           
           int filasafectadas = ps.executeUpdate();
           if(filasafectadas > 0 ){
               JOptionPane.showMessageDialog(null, "Producto Registrado Exitosamente");
               return true;
           }      
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "ERROR" + e);      
       }
       return false;  
   }
   
   public DefaultTableModel TablaProductos(){
       
        String[] columnas = {"N°", "Nombre", "Descripcion", "Precio Compra","Precio Venta","Stock","Categoria","Proveedor"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = """
                     SELECT 
                         productos.id, 
                         productos.nombre, 
                         productos.descripcion, 
                         productos.precio_compra,
                         productos.precio_venta, 
                         productos.stock, 
                         proveedores.nombre AS proveedor,
                         categorias.nombre AS categoria
                     FROM productos
                     INNER JOIN proveedores ON productos.proveedor_id = proveedores.id
                     INNER JOIN categorias ON productos.categoria_id = categorias.id""";

        try(
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) 
        {
            while (rs.next()) {
                Object[] fila = new Object[8];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getFloat("precio_compra");
                fila[4] = rs.getFloat("precio_venta");
                fila[5] = rs.getInt("stock");
                fila[6] = rs.getString("categoria");
                fila[7] = rs.getString("proveedor");       
                
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
   }
   
   public void RellenarCombobox(String tabla, String valor, JComboBox combo){
       
       String sql ="SELECT * FROM "+ tabla;
       try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ){
           while (rs.next()) {               
               
               //int id = rs.getInt("id");
               //String nombre = rs.getString(valor);          
               combo.addItem(rs.getString(valor));
           }
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "ERROR" + e.toString());
       }
   }
   
   public float getPrecio(String nombre){
      
    //continuar aqui querias obtener fecha precio imagen para colocar en la pantalla de ventas 
    String sql = "select  precio_venta from productos where nombre = ?;";
    float precio = 0;
      
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, nombre);

        ResultSet rs = pst.executeQuery();
        rs.next();
        precio  = rs.getFloat("precio_venta");
        

     } catch (Exception e) {
        System.err.println("Error : " + e.getMessage());
        }
    return precio;
   }
           
          
}
