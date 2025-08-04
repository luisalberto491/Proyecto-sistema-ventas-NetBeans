/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author Apoyo Docente
 */
public class Sesión {
    private static int id;
    private static String nombre;
    private static String correo;

    public Sesión(){
    
    }
    
    public static void iniciarSesion(int userId, String userNombre, String userCorreo) {
        id = userId;
        nombre = userNombre;
        correo = userCorreo;
    }

    public static int getId() {
        return id;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getCorreo() {
        return correo;
    }

    public static boolean estaLogueado() {
        return id != 0;
    }

    public static void cerrarSesion() {
        id = 0;
        nombre = null;
        correo = null;
    }
    
    public static void main(String[] args) {
       
        Sesión.iniciarSesion(1,"luis","luis@gmail.com");
        System.out.println(Sesión.estaLogueado()+" "+Sesión.getId()+" "+Sesión.getNombre()+" "+Sesión.getCorreo());  
        Sesión.cerrarSesion();
        System.out.println(Sesión.estaLogueado()+" "+Sesión.getId()+" "+Sesión.getNombre()+" "+Sesión.getCorreo());  

    }

}
