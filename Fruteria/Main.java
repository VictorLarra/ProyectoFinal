import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {
    
    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAO();
        int opcion = 0;

        do {
            String menu = """
                
                  "Bienvenido a nuestra frutería "La Económica" 
                  ¿Qué deseas hacer ? \n
                1. Agregar producto
                2. Listar productos
                3. Eliminar producto
                4. Consultar valor en dinero de los productos
                5. Salir
                """;
            String input = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                opcion = 3; // Salir si el usuario cierra el cuadro de diálogo
                continue;
            }

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

           
              switch (opcion) {
                case 1:

                    agregarProducto(productoDAO);
                    break;
                   case 2:
                    listarProductos(productoDAO);
                    break;
                  case 3:
                  eliminarProducto(productoDAO);
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema. ¡Hasta pronto!", "Salir", JOptionPane.INFORMATION_MESSAGE);
                    break;

                    case 4:
                    double sumaPrecios = productoDAO.calcularSumaPrecios(); //CALCULAR VALOR TOTAL
                    break;

                    case 5: 
                   JOptionPane.showMessageDialog(null, "El programa se cerrará");
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } while (opcion != 3);
    }

       private static void agregarProducto(ProductoDAO productoDAO) { // AGREGA NUEVOS PRODUCTOS
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", "Agregar Producto", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null || nombre.isBlank()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad del producto:", "Agregar Producto", JOptionPane.QUESTION_MESSAGE);
            int cantidad = Integer.parseInt(cantidadStr);

            String precioStr = JOptionPane.showInputDialog(null, "Ingrese el precio unitario del producto:", "Agregar Producto", JOptionPane.QUESTION_MESSAGE);
            double precio = Double.parseDouble(precioStr);

            Producto producto = new Producto(nombre, cantidad, precio);
            productoDAO.guardarProducto(producto);
     
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad o precio inválidos. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void listarProductos(ProductoDAO productoDAO) {//MUESTRA LA CANTIDAD DE PRODUCTOS
        List<Producto> productos = productoDAO.listarProductos();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos almacenados.", "Lista de Productos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder lista = new StringBuilder("Lista de Productos:\n");
            for (Producto producto : productos) {
                lista.append(producto).append("\n");
            }
            JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Productos", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static void eliminarProducto(ProductoDAO productoDAO) {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto a eliminar:", "Eliminar Producto", JOptionPane.QUESTION_MESSAGE);

        if (nombre != null && !nombre.trim().isEmpty()) {
            productoDAO.eliminarProducto(nombre);
        } else {
            JOptionPane.showMessageDialog(null, "El nombre del producto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}