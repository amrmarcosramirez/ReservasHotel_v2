package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;

public class MainApp {

    // Se crean los atributos con su visibilidad adecuada
    public static final int CAPACIDAD = 45;
    private Habitaciones habitaciones;
    private Reservas reservas;
    private Huespedes huespedes;

    // Main
    public static void main(String[] args) {

        Opcion opcion;
        do {
            mostrarMenu();
            opcion = elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);



    }

    private static void ejecutarOpcion(Opcion opcion){
        switch (opcion) {
            case Opcion.SALIR -> System.out.println("Hasta luego!!!");
            case Opcion.INSERTAR_HUESPED -> ;
            case Opcion.BUSCAR_HUESPED;
            case Opcion.BORRAR_HUESPED;
            case Opcion.MOSTRAR_HUESPEDES;
            case Opcion.INSERTAR_HABITACION;
            case Opcion.BUSCAR_HABITACION;
            case Opcion.BORRAR_HABITACION;
            case Opcion.MOSTRAR_HABITACIONES;
            case Opcion.INSERTAR_RESERVA;
            case Opcion.ANULAR_RESERVA;
            case Opcion.MOSTRAR_RESERVAS;
            case Opcion.CONSULTAR_DISPONIBILIDAD;
        }
    }

}
