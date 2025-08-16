package Base_De_Datos;

import java.sql.*;

public class ConexionBD {
    
    static String url="jdbc:mysql://localhost:3306/BDSistemaDeVentas";
    static String user="root";
    static String pass="admin";
    
    public static Connection conectar()
    {
       Connection con=null;
       try
       {
       con=DriverManager.getConnection(url,user,pass);
          System.out.println("Conexión exitosa");
       }catch(SQLException e)
       {
        e.printStackTrace();
       }
       return con;        
    }
     
    public static void main(String[] args) {
        ConexionBD bdc = new ConexionBD();
        bdc.conectar();
    }
}
