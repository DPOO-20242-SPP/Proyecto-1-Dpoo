package app;
import dao.*;
import Entidades.*;
import servicios.TransferService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UsuarioDao usuarioDAO = new UsuarioDao();
        VenueDao venueDAO = new VenueDao();
        EventoDao eventoDAO = new EventoDao();
        LocalidadDao localidadDAO = new LocalidadDao();
        TiqueteDao tiqueteDAO = new TiqueteDao();
        TransaccionDao transaccionDAO = new TransaccionDao();
        TransferService transferService = new TransferService();

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n-_-_-_- menu sistema de eventos-_-_-_- ");
            System.out.println("1  - Registrar usuario");
            System.out.println("2  - Listar usuarios");
            System.out.println("3  - Crear venue");
            System.out.println("4  - Listar venues");
            System.out.println("5  - Crear evento (organizador)");
            System.out.println("6  - Listar eventos");
            System.out.println("7  - Crear localidad y generar tiquetes");
            System.out.println("8  - Listar localidades por evento");
            System.out.println("9  - Listar tiquetes por localidad");
            System.out.println("10 - Comprar tiquete (transaccion)");
            System.out.println("11 - Listar transacciones por cliente");
            System.out.println("12 - Transferir tiquete");
            System.out.println("0  - Salir");
            System.out.print("Selecciona una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Opcion inválida, intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1: 
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();
                    System.out.print("Tipo (Cliente/Organizador/Administrador): ");
                    String tipo = sc.nextLine();
                    Usuario u = new Usuario(nombre, correo, pass, tipo);
                    usuarioDAO.insertar(u);
                    break;

                case 2: 
                    List<Usuario> usuarios = usuarioDAO.listar();
                    System.out.println("\n-_-_-_-Usuarios-_-_-_-");
                    for (Usuario us : usuarios) {
                        System.out.println(us.getId() + " | " + us.getNombre() + " | " + us.getCorreo() + " | " + us.getTipo());
                    }
                    break;

                case 3: 
                    System.out.print("Nombre venue: ");
                    String vn = sc.nextLine();
                    System.out.print("Direccion: ");
                    String vd = sc.nextLine();
                    Venue v = new Venue();
                    v.setNombre(vn);
                    v.setDireccion(vd);
                    int vid = venueDAO.insertar(v);
                    if (vid > 0) System.out.println("Venue creado con id " + vid);
                    break;

                case 4: 
                    List<Venue> venues = venueDAO.listar();
                    System.out.println("\n-_-_-_- Venues-_-_-_-");
                    for (Venue vv : venues) {
                        System.out.println(vv.getId() + " | " + vv.getNombre() + " | " + vv.getDireccion());
                    }
                    break;

                case 5: 
                    try {
                        Evento e = new Evento();
                        System.out.print("Nombre evento: ");
                        e.setNombre(sc.nextLine());
                        System.out.print("Descripcion: ");
                        e.setDescripcion(sc.nextLine());
                        System.out.print("Fecha (YYYY-MM-DD): ");
                        String fechaStr = sc.nextLine();
                        e.setFecha(Date.valueOf(fechaStr));
                        System.out.print("OrganizadorId: ");
                        e.setOrganizadorId(Integer.parseInt(sc.nextLine()));
                        System.out.print("VenueId: ");
                        e.setVenueId(Integer.parseInt(sc.nextLine()));
                        int eventoId = eventoDAO.insertar(e);
                        if (eventoId > 0) System.out.println("Evento creado con id " + eventoId);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("el formato no es correcto, utilice porfis =( YYYY-MM-DD.");
                    }
                    break;

                case 6: 
                    List<Evento> eventos = eventoDAO.listar();
                    System.out.println("\n-_-_-_-Eventos-_-_-_-");
                    for (Evento ev : eventos) {
                        System.out.println(ev.getId() + " | " + ev.getNombre() + " | fecha: " + ev.getFecha() + " | organizador: " + ev.getOrganizadorId() + " | venue: " + ev.getVenueId());
                    }
                    break;

                case 7: 
                    try {
                        Localidad l = new Localidad();
                        System.out.print("Nombre localidad: ");
                        l.setNombre(sc.nextLine());
                        System.out.print("Capacidad: ");
                        l.setCapacidad(Integer.parseInt(sc.nextLine()));
                        System.out.print("Precio: ");
                        l.setPrecio(Double.parseDouble(sc.nextLine()));
                        System.out.print("EventoId: ");
                        l.setEventoId(Integer.parseInt(sc.nextLine()));
                        System.out.print("¿Numerada? (true/false): ");
                        boolean numerada = Boolean.parseBoolean(sc.nextLine());
                        int locId = localidadDAO.insertar(l, numerada);
                        if (locId > 0) System.out.println("Localidad creada con id " + locId);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrada numerica inválida.");
                    }
                    break;

                case 8: 
                    try {
                        System.out.print("EventoId: ");
                        int evId = Integer.parseInt(sc.nextLine());
                        List<Localidad> locs = localidadDAO.listarPorEvento(evId);
                        System.out.println("\n-_-_-_-Localidades del evento " + evId + "-_-_-_-");
                        for (Localidad loc : locs) {
                            System.out.println(loc.getId() + " | " + loc.getNombre() + " | capacidad: " + loc.getCapacidad() + " | precio: " + loc.getPrecio());
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                    }
                    break;

                case 9: 
                    try {
                        System.out.print("LocalidadId: ");
                        int locIdQuery = Integer.parseInt(sc.nextLine());
                        List<Tiquete> tiqs = tiqueteDAO.listarPorLocalidad(locIdQuery);
                        System.out.println("\n-_-_-_-Tiquetes de localidad " + locIdQuery + " -_-_-_-");
                        for (Tiquete tiq : tiqs) {
                            System.out.println(tiq.getId() + " | " + tiq.getCodigo() + " | tipo: " + tiq.getTipo() + " | vendido: " + tiq.isVendido() + " | propietario: " + tiq.getPropietarioId());
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                    }
                    break;

                case 10: 
                    try {
                        System.out.print("ClienteId: ");
                        int clienteId = Integer.parseInt(sc.nextLine());
                        System.out.print("TiqueteId: ");
                        int tiqueteId = Integer.parseInt(sc.nextLine());
                        boolean ok = transaccionDAO.comprarTiquete(clienteId, tiqueteId);
                        if (!ok) System.out.println("Compra fallida.");
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                    }
                    break;

                case 11: 
                     try {
                        System.out.print("ClienteId: ");
                        int cId = Integer.parseInt(sc.nextLine());
                        List<Transaccion> tlist = transaccionDAO.listarPorCliente(cId);
                        System.out.println("\n--- Transacciones del cliente " + cId + " ---");
                        for (Transaccion tr : tlist) {
                            System.out.println(tr.getId() + " | tiquete: " + tr.getTiqueteId() + " | monto: " + tr.getMonto() + " | fecha: " + tr.getFechaCompra() + " | reservaOrg: " + tr.isEsReservaOrganizador());
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                    }
                     break;

                case 12: 
                    try {
                        System.out.print("TiqueteId: ");
                        int tId = Integer.parseInt(sc.nextLine());
                        System.out.print("From UsuarioId: ");
                        int from = Integer.parseInt(sc.nextLine());
                        System.out.print("To UsuarioId: ");
                        int to = Integer.parseInt(sc.nextLine());
                        boolean transOk = transferService.transferirTiquete(tId, from, to);
                        if (!transOk) System.out.println("Transferencia fallida.");
                    } catch (NumberFormatException ex) {
                        System.out.println("Id inválido.");
                    }
                    break;

                case 0:
                    System.out.println("saliendo");
                    break;

                default:
                    System.out.println("eliga otra opcion");
            } 
        } 

        sc.close();
        System.out.println("el programa se cerro y le va a explotar la pc");
    }
}

