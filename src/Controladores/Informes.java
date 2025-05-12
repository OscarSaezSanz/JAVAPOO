package Controladores;

import java.sql.*;

public class Informes {

    public Connection conexion;

    public void mostrarInformeVentasPorCliente() {
        String sql = """
                SELECT 
                    c.nombre AS cliente,
                    a.nombre AS articulo,
                    v.cantidad,
                    v.fecha_venta,
                    (v.cantidad * a.precio_unitario) AS subtotal
                FROM Ventas v
                JOIN Clientes c ON v.id_cliente = c.id_cliente
                JOIN Articulos a ON v.id_articulo = a.id_articulo
                ORDER BY c.nombre, v.fecha_venta
                """;

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            String clienteAnterior = "";
            double totalCliente = 0;

            while (rs.next()) {
                String cliente = rs.getString("cliente");
                String articulo = rs.getString("articulo");
                int cantidad = rs.getInt("cantidad");
                Date fecha = rs.getDate("fecha_venta");
                double subtotal = rs.getDouble("subtotal");

                if (!cliente.equals(clienteAnterior)) {
                    if (!clienteAnterior.equals("")) {
                        System.out.printf("Total gastado por %s: %.2f €\n\n", clienteAnterior, totalCliente);
                    }
                    System.out.println("Cliente: " + cliente);
                    clienteAnterior = cliente;
                    totalCliente = 0;
                }

                System.out.printf("  - %s | Cantidad: %d | Fecha: %s | Subtotal: %.2f €\n",
                        articulo, cantidad, fecha, subtotal);
                totalCliente += subtotal;
            }

            if (!clienteAnterior.equals("")) {
                System.out.printf("Total gastado por %s: %.2f €\n", clienteAnterior, totalCliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al generar informe: " + e.getMessage());
        }
    }
}

