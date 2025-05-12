package Controladores;

import java.sql.*;
import java.util.Scanner;

public class ControladorVentas {

    public Connection conexion;
    private final Scanner sc = new Scanner(System.in);

    public void crear() {
        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());
        System.out.print("ID del artículo: ");
        int idArticulo = Integer.parseInt(sc.nextLine());
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        System.out.print("Fecha de venta (YYYY-MM-DD): ");
        String fecha = sc.nextLine();

        String sql = "INSERT INTO Ventas (id_cliente, id_articulo, cantidad, fecha_venta) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idArticulo);
            ps.setInt(3, cantidad);
            ps.setDate(4, Date.valueOf(fecha));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Venta registrada con ID: " + rs.getInt(1));
                    }
                }
            } else {
                System.out.println("No se pudo registrar la venta.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar venta: " + e.getMessage());
        }
    }

    public void listar() {
        System.out.println("\nListado de Ventas:\n");

        String sql = """
                SELECT v.id_venta, c.nombre AS cliente, a.nombre AS articulo, v.cantidad, v.fecha_venta
                FROM Ventas v
                JOIN Clientes c ON v.id_cliente = c.id_cliente
                JOIN Articulos a ON v.id_articulo = a.id_articulo
                """;

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID Venta:   " + rs.getInt("id_venta"));
                System.out.println("Cliente:    " + rs.getString("cliente"));
                System.out.println("Artículo:   " + rs.getString("articulo"));
                System.out.println("Cantidad:   " + rs.getInt("cantidad"));
                System.out.println("Fecha:      " + rs.getDate("fecha_venta"));
                System.out.println("\n-----------------------------\n");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
    }

    public void modificar() {
        System.out.print("ID de la venta a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo ID del artículo: ");
        int idArticulo = Integer.parseInt(sc.nextLine());
        System.out.print("Nueva cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();

        String sql = "UPDATE Ventas SET id_cliente = ?, id_articulo = ?, cantidad = ?, fecha_venta = ? WHERE id_venta = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idArticulo);
            ps.setInt(3, cantidad);
            ps.setDate(4, Date.valueOf(fecha));
            ps.setInt(5, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Venta actualizada correctamente." : "Venta no encontrada.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar venta: " + e.getMessage());
        }
    }

    public void eliminar() {
        System.out.print("ID de la venta a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Ventas WHERE id_venta = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Venta eliminada correctamente." : "Venta no encontrada.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
        }
    }
}

