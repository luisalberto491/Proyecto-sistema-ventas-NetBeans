/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
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
   public boolean insertarProducto(String nombre, String descripcion, double precio, String url_imagen, int stock, int categoria, int proveedor){
    
       String sql = """
                    INSERT INTO producto (nombre, descripcion, precio, stock, url_imagen, id_categoria, id_proveedor) VALUES
                    (?, ?, ?, ?, ?, ?, ?)""";
               
       try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ){
           
           ps.setString(1, nombre);
           ps.setString(2, descripcion);
           ps.setDouble(3, precio);
           ps.setFloat(4, stock);
           ps.setString(5, url_imagen);
           ps.setInt(6, categoria);
           ps.setInt(7, proveedor);
           
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
   
    public DefaultTableModel TablaProductos() {
    
            String[] columnas = {"N°", "Nombre", "Descripcion", "Precio", "Stock", "Imagen", "Categoria", "Proveedor"};
            DefaultTableModel modelo = new DefaultTableModel(null, columnas);

            String sql = """
                         SELECT 
                             producto.id, 
                             producto.nombre, 
                             producto.descripcion, 
                             producto.precio,
                             producto.stock, 
                             producto.url_imagen,
                             categoria.nombre AS categoria,
                             proveedor.nombre AS proveedor
                         FROM producto
                         INNER JOIN proveedor ON producto.id_proveedor = proveedor.id
                         INNER JOIN categoria ON producto.id_categoria = categoria.id
                         """;

            try (Connection conn = ConexionBD.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Object[] fila = new Object[8];
                    fila[0] = rs.getInt("id");
                    fila[1] = rs.getString("nombre");
                    fila[2] = rs.getString("descripcion");
                    fila[3] = rs.getFloat("precio");
                    fila[4] = rs.getFloat("stock");

                    String imagePath = rs.getString("url_imagen");
                    ImageIcon icon;
                    try {
                        Image img = new ImageIcon(imagePath).getImage();
                        icon = new ImageIcon(img.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                    } catch (Exception e) {
                        // Imagen por defecto en caso de error
                        icon = new ImageIcon(new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB));
                    }
                    fila[5] = icon;

                    fila[6] = rs.getString("categoria");
                    fila[7] = rs.getString("proveedor");

                    modelo.addRow(fila);
                }
            } catch (Exception e) {
            }

        return modelo;
    }

   
   public void RellenarCombobox(String tabla, String valor, JComboBox combo){
       
       String sql ="SELECT nombre FROM "+ tabla;
       try (
            Connection conn = ConexionBD.conectar();    
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ){
           while (rs.next()) {                           
               combo.addItem(rs.getString(valor));
           }          
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "ERROR" + e.toString());
       }
   }
   
   public float getPrecio(String nombre){
      
    String sql = "select  precio from producto where nombre = ?;";
    float precio = 0;
      
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, nombre);

        ResultSet rs = pst.executeQuery();
        rs.next();
        precio  = rs.getFloat("precio");
        

     } catch (Exception e) {
        System.err.println("Error : " + e.getMessage());
        }
    return precio;
   }
           
  public String UrlImagenProducto(String nombre){
      
      String sql = "select url_imagen from producto where nombre = ?;";
      String imagen = "";
      
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, nombre);

        ResultSet rs = pst.executeQuery();
        rs.next();
        imagen  = rs.getString("url_imagen");
        

     } catch (Exception e) {
        System.err.println("Error : " + e.getMessage());
        }
     return imagen;
  }       
}
