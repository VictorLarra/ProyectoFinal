import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/productos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    public void guardarProducto(Producto producto) { //AGREGAR UN NUEVO PRODUCTO
        String sql = "INSERT INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setInt(2, producto.getCantidad());
            statement.setDouble(3, producto.getPrecio());
            statement.executeUpdate();

            System.out.println("Producto agregado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar producto: " + e.getMessage());
        }
    }

    public List<Producto> listarProductos() { // MOSTRAR LA LISTA DE PRODUCTOS
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, cantidad, precio FROM productos";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                Producto producto = new Producto(id, nombre, cantidad, precio);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }

        return productos;
    }



    public void eliminarProducto(String nombre) { // CONSULTAR PARA ELIMINAR EL PRODUCTO 
        
        String sql = "DELETE FROM productos WHERE nombre = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nombre);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Producto '" + nombre + "' eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el producto con el nombre: " + nombre);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
        }
    }



    public double calcularSumaPrecios() { // CALCULAR VALOR TOTAL DE LOS PRECIOS 
        double sumaPrecios = 0.0; // Variable para almacenar la suma de los precios

        // Consulta SQL para calcular la suma de los precios
        String sql = "SELECT SUM(precio) AS total_precio FROM productos";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                sumaPrecios = resultSet.getDouble("total_precio");
                System.out.println("La suma total de precios es de: "+ sumaPrecios);
            }

        } catch (SQLException e) {
            System.err.println("Error al calcular la suma de precios: " + e.getMessage());
        }

        return sumaPrecios;
    }





}