package practica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private final String bd = "Prueba"; 
    private final String url = "jdbc:mysql://localhost/" + bd;
    private final String user = "root";
    private final String password = "123456"; 

    public Connection conectar() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return connection;
    }

    public void desconectar(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Desconexión exitosa");
            } catch (SQLException e) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.conectar(); 
        conexion.desconectar(conn); 
    }
}

