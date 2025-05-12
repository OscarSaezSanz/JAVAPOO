package Controladores;

import java.sql.*;
import java.util.Scanner;

public class ControladorFacturasRecibidas {

    public Connection conexion;
    private final Scanner sc = new Scanner(System.in);

    public void crear() {
        System.out.print("ID del proveedor: ");
        int idProveedor = Integer.parseInt(sc.nextLine());
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Total factura: ");
        double total = Double.parseDouble(sc.nextLine());

        String sql = "INSERT INTO Facturas_Recibidas (id_proveedor, fecha, total) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idProveedor);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setDouble(3, total);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Factura creada con ID: " + rs.getInt(1));
                    }
                }
            } else {
                System.out.println("No se pudo crear la factura.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar factura: " + e.getMessage());
        }
    }

    
    public void listar() {
        System.out.println("\nListado de Facturas Recibidas:\n");
        String sql = """
                SELECT f.id_factura, p.nombre AS proveedor, f.fecha, f.total
                FROM Facturas_Recibidas f
                JOIN Proveedores p ON f.id_proveedor = p.id_proveedor
                """;

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID Factura: " + rs.getInt("id_factura"));
                System.out.println("Proveedor:  " + rs.getString("proveedor"));
                System.out.println("Fecha:      " + rs.getDate("fecha"));
                System.out.println("Total:      " + rs.getDouble("total"));
                System.out.println("\n-----------------------------\n");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }
    }

    public void modificar() {
        System.out.print("ID de la factura a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo ID del proveedor: ");
        int idProveedor = Integer.parseInt(sc.nextLine());
        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Nuevo total: ");
        double total = Double.parseDouble(sc.nextLine());

        String sql = "UPDATE Facturas_Recibidas SET id_proveedor = ?, fecha = ?, total = ? WHERE id_factura = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setDouble(3, total);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Factura actualizada correctamente." : "Factura no encontrada.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar factura: " + e.getMessage());
        }
    }

    public void eliminar() {
        System.out.print("ID de la factura a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Facturas_Recibidas WHERE id_factura = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Factura eliminada correctamente." : "Factura no encontrada.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
        }
    }
}

