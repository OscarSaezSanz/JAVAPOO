package Controladores;


import java.sql.*;
import java.util.Scanner;

public class ControladorProveedores {

    public Connection conexion;
    private final Scanner sc = new Scanner(System.in);

    public void crear() {
        System.out.print("Nombre del proveedor: ");
        String nombre = sc.nextLine();
        System.out.print("CIF: ");
        String cif = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        String sql = "INSERT INTO Proveedores (nombre, cif, telefono) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, cif);
            ps.setString(3, telefono);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Proveedor creado con ID: " + rs.getInt(1));
                    }
                }
            } else {
                System.out.println("No se pudo crear el proveedor.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
        }
    }

    public void listar() {
        System.out.println("\nListado de Proveedores:\n");
        String consulta = "SELECT id_proveedor, nombre, cif, telefono FROM Proveedores";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_proveedor"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("CIF: " + rs.getString("cif"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("\n-----------------------------\n");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
    }

    public void modificar() {
        System.out.print("ID del proveedor a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo CIF: ");
        String cif = sc.nextLine();
        System.out.print("Nuevo teléfono: ");
        String telefono = sc.nextLine();

        String sql = "UPDATE Proveedores SET nombre = ?, cif = ?, telefono = ? WHERE id_proveedor = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, cif);
            ps.setString(3, telefono);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Proveedor actualizado correctamente." : "Proveedor no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
        }
    }

    public void eliminar() {
        System.out.print("ID del proveedor a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Proveedores WHERE id_proveedor = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Proveedor eliminado correctamente." : "Proveedor no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
        }
    }
}

