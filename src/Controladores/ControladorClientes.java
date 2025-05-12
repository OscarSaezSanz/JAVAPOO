package Controladores;


import java.sql.*;
import java.util.*;

public class ControladorClientes {
	public Connection conexion;
    Scanner sc = new Scanner(System.in);
	public void crear() {
		
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        String sql = "INSERT INTO Clientes (nombre, email, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, telefono);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("Cliente creado con ID: " + id);
                    }
                }
            } else {
                System.out.println("No se pudo crear el cliente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    public void listar() {
        System.out.println("\nListado de Clientes:\n");
        String consulta = "SELECT id_cliente, nombre, email, telefono FROM Clientes";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                System.out.println("ID Cliente: " + rs.getInt("id_cliente"));
                System.out.println("Nombre:     " + rs.getString("nombre"));
                System.out.println("Email:      " + rs.getString("email"));
                System.out.println("Teléfono:   " + rs.getString("telefono"));
                System.out.println("\n──────────────────────────────────────\n");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
    }

    public void modificar() {
        System.out.print("ID del cliente a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo email: ");
        String email = sc.nextLine();
        System.out.print("Nuevo teléfono: ");
        String telefono = sc.nextLine();

        String sql = "UPDATE Clientes SET nombre = ?, email = ?, telefono = ? WHERE id_cliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, telefono);
            ps.setInt(4, id);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Cliente actualizado correctamente." : "Cliente no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminar() {
        System.out.print("ID del cliente a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        String sql = "DELETE FROM Clientes WHERE id_cliente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Cliente eliminado correctamente." : "Cliente no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
	
}

