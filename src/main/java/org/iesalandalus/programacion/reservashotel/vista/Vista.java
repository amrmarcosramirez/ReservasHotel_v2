package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;

public class Vista {
    // Se crean los atributos con su visibilidad adecuada
    private Controlador controlador;

    public Vista () {}

    //Se crean los métodos
    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "ERROR: El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            mostrarMenu();
            opcion = elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("Hasta luego.!!!");
    }

    private void ejecutarOpcion(Opcion opcion){
        switch (opcion) {
            case SALIR -> controlador.terminar();
            case INSERTAR_HUESPED -> insertarHuesped();
            case BUSCAR_HUESPED -> buscarHuesped();
            case BORRAR_HUESPED -> borrarHuesped();
            case MOSTRAR_HUESPEDES -> mostrarHuespedes();
            case INSERTAR_HABITACION -> insertarHabitacion();
            case BUSCAR_HABITACION -> buscarHabitacion();
            case BORRAR_HABITACION -> borrarHabitacion();
            case MOSTRAR_HABITACIONES -> mostrarHabitaciones();
            case INSERTAR_RESERVA -> insertarReserva();
            case ANULAR_RESERVA -> anularReserva();
            case MOSTRAR_RESERVAS -> mostrarReservas();
            case CONSULTAR_DISPONIBILIDAD -> consultarDisponibilidad(
                    leerTipoHabitacion(),
                    leerFecha("Introduce la fecha de inicio de reserva (%s): "),
                    leerFecha("Introduce la fecha de fin de reserva (%s): "));
            case REALIZAR_CHECKIN -> realizarCheckin();
            case REALIZAR_CHECKOUT -> realizarCheckOut();
        }
    }

    private void insertarHuesped(){
        String mensaje = "Insertar huésped";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Huesped huesped = leerHuesped();
            controlador.insertar(huesped);
            System.out.println("Huésped insertado correctamente.");
        } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarHuesped(){
        String mensaje = "Buscar huésped";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Huesped huesped = leerClientePorDni();
            Huesped huesped1 = controlador.buscar(huesped);
            if (huesped1 != null) {
                System.out.println("El huésped buscado es: " + huesped1);
            } else {
                System.out.println("No existe ningún huésped con dicho DNI.");
            }
        }catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void borrarHuesped(){
        String mensaje = "Borrar huésped";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Huesped huesped = leerClientePorDni();
            Huesped huesped1 = controlador.buscar(huesped);
            if(huesped1 == null) {
                System.out.println("No existe ningún huésped con dicho DNI.");
            } else {
                controlador.borrar(huesped1);
                System.out.println("Huésped borrado correctamente.");
            }
        } catch (OperationNotSupportedException | IllegalArgumentException |
                 NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarHuespedes(){
        Stream<Huesped> listaHuespedes = Arrays.stream(controlador.getHuespedes()).filter(Objects::nonNull);
        int noNulos = (int) listaHuespedes.count();
        if (noNulos > 0) {
            for (Object huesped : listaHuespedes.toArray()) {
                System.out.println(huesped);
            }
        } else {
            System.out.println("No hay huéspedes que mostrar.");
        }
    }

    private void insertarHabitacion(){
        String mensaje = "Insertar habitación";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Habitacion habitacion = leerHabitacion();
            controlador.insertar(habitacion);
            System.out.println("Habitación insertada correctamente.");
        } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarHabitacion(){
        String mensaje = "Buscar habitación";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Habitacion habitacion = leerHabitacionPorIdentificador();
            Habitacion habitacion1 = controlador.buscar(habitacion);
            if (habitacion1 != null) {
                System.out.println("La habitación buscada es: " + habitacion1);
            } else {
                System.out.println("La habitación buscada no existe.");
            }
        }catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void borrarHabitacion(){
        String mensaje = "Borrar habitacción";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Habitacion habitacion = leerHabitacionPorIdentificador();
            Habitacion habitacion1 = controlador.buscar(habitacion);
            if(habitacion1 == null) {
                System.out.println("No existe la habitación indicada.");
            } else {
                controlador.borrar(habitacion1);
                System.out.println("Habitación borrada correctamente.");
            }
        } catch (OperationNotSupportedException | IllegalArgumentException |
                 NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarHabitaciones(){
        Stream<Habitacion> listaHabitacion = Arrays.stream(controlador.getHabitaciones()).filter(Objects::nonNull);
        int noNulos = (int) listaHabitacion.count();
        if (noNulos > 0) {
            for (Object habitacion : listaHabitacion.toArray()) {
                System.out.println(habitacion);
            }
        } else {
            System.out.println("No hay habitaciones que mostrar.");
        }
    }

    private void insertarReserva(){
        String mensaje = "Insertar reserva";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            Reserva reserva = leerReserva();
            Habitacion habitacionDisponible = consultarDisponibilidad(reserva.getHabitacion().getTipoHabitacion(),
                    reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());
            if (!(habitacionDisponible==null)) {
                reserva.setHabitacion(habitacionDisponible);
                controlador.insertar(reserva);
                System.out.println("Reserva insertada correctamente.");
            } else {
                System.out.println("El tipo de habitación a reservar NO está disponible.");
            }
        } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarReservas(Huesped huesped){
        Stream<Reserva> reservas = Arrays.stream(controlador.getReservas()).filter(Objects::nonNull);
        int noNulos = (int) reservas.count();
        if (noNulos > 0) {
            int i = 0;
            for (Object reserva1 : reservas.toArray()) {
                System.out.println(i + ".- " + reserva1);
                i++;
            }
        } else {
            System.out.println("No hay reservas que listar.");
        }
    }

    private void listarReservas(TipoHabitacion tipoHabitacion){
        Stream<Reserva> reservas = Arrays.stream(controlador.getReservas(tipoHabitacion)).filter(Objects::nonNull);
        int noNulos = (int) reservas.count();
        if (noNulos > 0) {
            int i = 0;
            for (Object reserva1 : reservas.toArray()) {
                System.out.println(i + ".- " + reserva1);
                i++;
            }
        } else {
            System.out.println("No hay reservas que listar.");
        }
    }

    private Reserva[] getReservasAnulables(Reserva[] reservasAAnular){
        Reserva[] reservasAnulables = new Reserva[reservasAAnular.length];
        int i = 0;
        for (Reserva reserva2 : reservasAAnular) {
            if(reserva2.getFechaInicioReserva().isAfter(LocalDate.now())){
                reservasAnulables[i] = reserva2;
                i++;
            }
        }
        return reservasAnulables;
    }

    private void anularReserva(){
        char confReserva = 'S';
        try {
            Huesped huesped = leerClientePorDni();
            Huesped huesped1 = controlador.buscar(huesped);
            if (huesped1 != null) {
                Reserva[] reservas1 = controlador.getReservas(huesped1);
                reservas1 = getReservasAnulables(reservas1);
                if (reservas1.length > 0) {
                    int i = 0;
                    for (Reserva reserva2 : reservas1) {
                        System.out.println(i + ".- " + reserva2);
                        i++;
                    }
                    System.out.println("Elija la reserva que desea anular.");
                    int numReserva = Entrada.entero();

                    if (reservas1.length == 1){
                        System.out.println("Confirma que desea anular la reserva (S/N)?");
                        confReserva = Entrada.caracter();
                    }
                    if (confReserva=='S'){
                        controlador.borrar(reservas1[numReserva]);
                        System.out.println("Reserva anulada correctamente.");
                    } else {
                        System.out.println("La reserva no ha sido anulada.");
                    }
                } else {
                    System.out.println("No hay reservas que se puedan anular.");
                }
            } else {
                System.out.println("No existe ningún huésped con dicho DNI.");
            }
        }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarReservas(){
        Stream<Reserva> listaReserva = Arrays.stream(controlador.getReservas()).filter(Objects::nonNull);
        int noNulos = (int) listaReserva.count();
        if (noNulos > 0) {
            for (Object reserva1 : listaReserva.toArray()) {
                System.out.println(reserva1);
            }
        } else {
            System.out.println("No hay reservas que mostrar.");
        }
    }

    private Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion,
                                                      LocalDate fechaInicioReserva,
                                                      LocalDate fechaFinReserva){
        try {
            if (tipoHabitacion == null) {
                throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un tipo de habitación nulo.");
            }
            if (fechaInicioReserva.isBefore(LocalDate.now())) {
                throw new NullPointerException("ERROR: La fecha de inicio no debe ser anterior al día de hoy.");
            }
            if (!fechaFinReserva.isAfter(fechaInicioReserva)) {
                throw new NullPointerException("ERROR: La fecha de fin de reserva debe ser posterior a la fecha de inicio de reserva.");
            }

            Habitacion[] habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);

            if (habitacionesTipoSolicitado==null){
                return null;
            } else {
                for (Habitacion habitacion : habitacionesTipoSolicitado){
                    Reserva[] reservasFuturas = controlador.getReservaFuturas(habitacion);
                    int numElementosNoNulos = getNumElementosNoNulos(reservasFuturas);
                    if (numElementosNoNulos==0) {
                        return habitacion;
                    } else {
                        Arrays.sort(reservasFuturas, 0, numElementosNoNulos,
                                Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                        if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())){
                            return habitacion;
                        } else {
                            Arrays.sort(reservasFuturas, 0, numElementosNoNulos,
                                    Comparator.comparing(Reserva::getFechaInicioReserva));

                            if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())){
                                return habitacion;
                            } else {
                                Habitacion habitacionDisponible = null;
                                boolean tipoHabitacionEncontrada = false;
                                for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                                {
                                    if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                                    {
                                        if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                                fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva()))
                                        {
                                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[j]);
                                            tipoHabitacionEncontrada = true;
                                        }
                                    }
                                }
                                return habitacionDisponible;
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int getNumElementosNoNulos(Reserva[] reservas) {
        return (int) Arrays.stream(reservas).filter(Objects::nonNull).count();
    }

    private void realizarCheckin() {
        try {
            Huesped huesped = leerClientePorDni();
            Huesped huesped1 = controlador.buscar(huesped);
            listarReservas(huesped1);
            System.out.println("Elija la reserva a la que desea realizar el checkin.");
            int numReserva = Entrada.entero();
            Reserva reserva = controlador.getReservas(huesped1)[numReserva];
            listarReservas(reserva.getHabitacion().getTipoHabitacion());
            String mensaje = "Introduce la fecha y hora de checkin de la reserva (%s): ";
            LocalDateTime fechaHora = leerFechaHora(mensaje);
            controlador.realizarCheckin(reserva, fechaHora);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void realizarCheckOut() {
        try {
            Huesped huesped = leerClientePorDni();
            Huesped huesped1 = controlador.buscar(huesped);
            listarReservas(huesped1);
            System.out.println("Elija la reserva a la que desea realizar el checkin.");
            int numReserva = Entrada.entero();
            Reserva reserva = controlador.getReservas(huesped1)[numReserva];
            listarReservas(reserva.getHabitacion().getTipoHabitacion());
            String mensaje = "Introduce la fecha y hora de checkin de la reserva (%s): ";
            LocalDateTime fechaHora = leerFechaHora(mensaje);
            controlador.realizarCheckout(reserva, fechaHora);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}
