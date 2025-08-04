/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Apoyo Docente
 */
public class LoginController {
    
    
    public LoginController(){
        
    }
    
    public boolean AuthLogin(String email, String password) {
    String sql = "SELECT * FROM usuarios WHERE email = ? AND contraseña = ?";
   
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, email);
        pst.setString(2, password);

        try (ResultSet rs = pst.executeQuery()) {
            
         if (rs.next()) { // Mover al primer resultado si existe
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String correo = rs.getString("email");

            Sesión.iniciarSesion(id, nombre, correo);
            return true; // Usuario encontrado
         } else {
            return false; // No se encontró usuario
         }
        }

     } catch (SQLException e) {
        System.err.println("Error al autenticar: " + e.getMessage());
        return false;
        }
    }
    public static void main(String[] args) {
        
        LoginController log = new LoginController();
        
        boolean bol = log.AuthLogin("juan@example.com", "123456");
        System.out.println(bol);
              
    }

}
