package Controladores;

import java.sql.*;
import java.util.Scanner;

public class ControladorArticulos {

    public Connection conexion;
    private final Scanner sc = new Scanner(System.in);

    public void crear() {
        System.out.print("Nombre del artículo: ");
        String nombre = sc.nextLine();
        System.out.print("Precio unitario: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO Articulos (nombre, precio_unitario, stock) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Artículo creado con ID: " + rs.getInt(1));
                    }
                }
            } else {
                System.out.println("No se pudo crear el artículo.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar artículo: " + e.getMessage());
        }
    }

    public void listar() {
        System.out.println("\nListado de Artículos:\n");
        String consulta = "SELECT id_articulo, nombre, precio_unitario, stock FROM Articulos";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_articulo"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Precio: " + rs.getDouble("precio_unitario"));
                System.out.println("Stock: " + rs.getInt("stock"));
                System.out.println("\n-----------------------------\n");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        }
    }

    public void modificar() {
        System.out.print("ID del artículo a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo precio unitario: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.print("Nuevo stock: ");
        int stock = Integer.parseInt(sc.nextLine());

        String sql = "UPDATE Articulos SET nombre = ?, precio_unitario = ?, stock = ? WHERE id_articulo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Artículo actualizado correctamente." : "Artículo no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar artículo: " + e.getMessage());
        }
    }

    public void eliminar() {
        System.out.print("ID del artículo a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Articulos WHERE id_articulo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Artículo eliminado correctamente." : "Artículo no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo: " + e.getMessage());
        }
    }
}
