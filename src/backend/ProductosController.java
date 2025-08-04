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
 * @author Apoyo Docente
 */
public class ProductosController {
 
    
   public ProductosController(){
       
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
}
