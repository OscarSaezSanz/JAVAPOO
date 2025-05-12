package Vista;

import java.sql.Connection;
import java.util.Scanner;

import Conexion.Conexion;
import Controladores.*;

public class VistaMenu {

    private final Scanner sc = new Scanner(System.in);

    public void iniciar() {
        Conexion gestor = new Conexion();
        gestor.conectar();
        Connection conexion = gestor.getConexion();

        if (conexion != null) {
            ControladorClientes controladorClientes = new ControladorClientes();
            controladorClientes.conexion = conexion;

            ControladorProveedores controladorProveedores = new ControladorProveedores();
            controladorProveedores.conexion = conexion;

            ControladorArticulos articulos = new ControladorArticulos();
            articulos.conexion = conexion;
            
            ControladorFacturasRecibidas facturas = new ControladorFacturasRecibidas();
            facturas.conexion = conexion;
            
            ControladorVentas ventas = new ControladorVentas();
            ventas.conexion = conexion;
            
            Informes informes = new Informes();
            informes.conexion = conexion;

            MenuVista(controladorClientes, controladorProveedores, articulos, facturas, ventas, informes);
        } else {
            System.err.println("No se pudo establecer conexión.");
        }

        gestor.cerrar();
        sc.close();
        System.out.println("Programa finalizado.");
    }

    public void MenuVista(ControladorClientes clientes, ControladorProveedores proveedores, ControladorArticulos articulos, ControladorFacturasRecibidas facturas, ControladorVentas ventas, Informes informes) {
        int opcion;
        do {
            System.out.println("\n-_-_-_-_-MENÚ-_-_-_-_-\n");
            System.out.println("1 - Gestion de Clientes");
            System.out.println("2 - Gestion de Proveedores");
            System.out.println("3 - Gestion de Articulos");
            System.out.println("4 - Gestion de Facturas Recibidas");
            System.out.println("5 - Gestion de Ventas");
            System.out.println("6 - Informe de Ventas por Cliente");
            System.out.println("7 - Salir");
            System.out.print("Elige una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
            	case 1 -> GestionMenuClientes(clientes);
            	case 2 -> GestionMenuProveedores(proveedores);
            	case 3 -> GestionMenuArticulos(articulos);
            	case 4 -> GestionMenuFacturas(facturas);
            	case 5 -> GestionMenuVentas(ventas);
            	case 6 -> informes.mostrarInformeVentasPorCliente();
                case 7 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 7);
    }

    public void GestionMenuClientes(ControladorClientes controlador) {
        int opcion;
        do {
            System.out.println("\n----- Gestión de Clientes ------\n");
            System.out.println("1 - Crear Nuevo Registro");
            System.out.println("2 - Listar Registros");
            System.out.println("3 - Modificar Registro");
            System.out.println("4 - Eliminar Registro");
            System.out.println("5 - Volver");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> controlador.crear();
                case 2 -> controlador.listar();
                case 3 -> controlador.modificar();
                case 4 -> controlador.eliminar();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }

    public void GestionMenuProveedores(ControladorProveedores controlador) {
        int opcion;
        do {
            System.out.println("\n----- Gestión de Proveedores ------\n");
            System.out.println("1 - Crear Nuevo Proveedor");
            System.out.println("2 - Listar Proveedores");
            System.out.println("3 - Modificar Proveedor");
            System.out.println("4 - Eliminar Proveedor");
            System.out.println("5 - Volver");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> controlador.crear();
                case 2 -> controlador.listar();
                case 3 -> controlador.modificar();
                case 4 -> controlador.eliminar();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }
    public void GestionMenuArticulos(ControladorArticulos controlador) {
        int opcion;
        do {
            System.out.println("\n----- Gestión de Artículos ------\n");
            System.out.println("1 - Crear Nuevo Artículo");
            System.out.println("2 - Listar Artículos");
            System.out.println("3 - Modificar Artículo");
            System.out.println("4 - Eliminar Artículo");
            System.out.println("5 - Volver");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> controlador.crear();
                case 2 -> controlador.listar();
                case 3 -> controlador.modificar();
                case 4 -> controlador.eliminar();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }
    public void GestionMenuFacturas(ControladorFacturasRecibidas controlador) {
        int opcion;
        do {
            System.out.println("\n----- Gestión de Facturas Recibidas ------\n");
            System.out.println("1 - Crear Nueva Factura");
            System.out.println("2 - Listar Facturas");
            System.out.println("3 - Modificar Factura");
            System.out.println("4 - Eliminar Factura");
            System.out.println("5 - Volver");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> controlador.crear();
                case 2 -> controlador.listar();
                case 3 -> controlador.modificar();
                case 4 -> controlador.eliminar();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }
    public void GestionMenuVentas(ControladorVentas controlador) {
        int opcion;
        do {
            System.out.println("\n----- Gestión de Ventas ------\n");
            System.out.println("1 - Crear Nueva Venta");
            System.out.println("2 - Listar Ventas");
            System.out.println("3 - Modificar Venta");
            System.out.println("4 - Eliminar Venta");
            System.out.println("5 - Volver");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> controlador.crear();
                case 2 -> controlador.listar();
                case 3 -> controlador.modificar();
                case 4 -> controlador.eliminar();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }
}

