/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import Base_De_Datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 *
 * @author Apoyo Docente
 */
public class RegisterController {
        

    public RegisterController(){
        
    }
    
    public boolean AuthRegister(String nombre,String email,String contraseña) {
    String sql = "INSERT INTO usuarios (nombre, email, contraseña, estado) VALUES (?, ?, ?, ?)";

    try (
        Connection cn = ConexionBD.conectar();
        PreparedStatement ps = cn.prepareStatement(sql);) {
        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, contraseña);
        ps.setBoolean(4, true);

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "¡Usuario registrado correctamente!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario.");
            return false;
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        return false;
    }
}

}
