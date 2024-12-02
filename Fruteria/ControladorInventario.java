import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ControladorInventario {




public void agregarProducto(Producto producto) {
    String sql = "INSERT INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
    String url = "jdbc:postgresql://localhost:5432/productos";
    String user = "postgres";
    String password = "12345";
    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement(sql)) 
 {

        statement.setString(1, producto.getNombre());
        statement.setInt(2, producto.getCantidad());
        statement.setDouble(3, producto.getPrecio());  


        int filasAfectadas = statement.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Producto agregado correctamente.");
        } else {
            System.out.println("Error  al agregar el producto.");
        }

    } catch (SQLException e) {
        System.err.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}



    // ... (otros métodos)
}