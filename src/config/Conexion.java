package config;

import view.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Connection conectar=null;
    String servidor = "127.0.0.1";
    String baseDatos = "ticket_sys_db";
    String usuario = "root";
    String password = "";
    public Connection conectar(){
       try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          /*conectar=DriverManager.getConnection("jdbc:mysql:"
                  + "//localhost/productos", "root",""); */
          conectar=DriverManager.getConnection("jdbc:mysql:"
                    + "//"+ servidor + "/" + baseDatos, usuario, password);
       } catch (Exception e) {
           Alert.error(e.getMessage(), "ERROR");
       }
       return conectar;
    }
}
