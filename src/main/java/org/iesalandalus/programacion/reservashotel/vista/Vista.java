package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;

public class Vista {

    // Se crean los atributos con su visibilidad adecuada
    private Controlador controlador;

    // Se crean el constructor
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

    private void ejecutarOpcion(Opcion opcion) {
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
            case CONSULTAR_DISPONIBILIDAD -> {
                String mensaje = "Consultar disponibilidad";
                System.out.printf("%n%s%n", mensaje);
                String cadena = "%0" + mensaje.length() + "d%n";
                System.out.println(String.format(cadena, 0).replace("0", "-"));

                if (!(controlador.getHabitaciones().size() > 0)) {
                    System.out.println("No hay habitaciones dadas de alta en el sistema.");
                } else {
                    Habitacion habitacionDisponible = consultarDisponibilidad(
                            leerTipoHabitacion(),
                            leerFecha("Introduce la fecha de inicio de reserva (%s): "),
                            leerFecha("Introduce la fecha de fin de reserva (%s): "));
                    if (habitacionDisponible == null) {
                        System.out.println("El tipo de habitación NO está disponible.");
                    } else {
                        System.out.println("La habitación: " + habitacionDisponible + ", está disponible.");
                    }
                }
            }
            case REALIZAR_CHECKIN -> realizarCheckin();
            case REALIZAR_CHECKOUT -> realizarCheckOut();
        }
    }

    private void insertarHuesped() {
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

    private void buscarHuesped() {
        String mensaje = "Buscar huésped";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHuespedes().size() > 0)) {
                System.out.println("No hay huéspedes dados de alta en el sistema.");
            } else {
                Huesped huesped = leerClientePorDni();
                Huesped huesped1 = controlador.buscar(huesped);
                if (huesped1 != null) {
                    System.out.println("El huésped buscado es: " + huesped1);
                } else {
                    System.out.println("No existe ningún huésped con dicho DNI.");
                }
            }
        } catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void borrarHuesped() {
        String mensaje = "Borrar huésped";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHuespedes().size() > 0)) {
                System.out.println("No hay huéspedes dados de alta en el sistema.");
            } else {
                Huesped huesped = leerClientePorDni();
                Huesped huesped1 = controlador.buscar(huesped);
                if (huesped1 == null) {
                    System.out.println("No existe ningún huésped con dicho DNI.");
                } else {
                    controlador.borrar(huesped1);
                    System.out.println("Huésped borrado correctamente.");
                }
            }
        } catch (OperationNotSupportedException | IllegalArgumentException |
                 NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarHuespedes(){
        String mensaje = "Mostrar huéspedes";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        List<Huesped> listaHuespedes = controlador.getHuespedes();
        if (listaHuespedes.size() > 0) {
            listaHuespedes.sort(Comparator.comparing(Huesped::getNombre));
            listaHuespedes.forEach(huesped1 -> System.out.println(huesped1.toString()));
            /*for (Huesped huesped : listaHuespedes) {
                System.out.println(huesped);
            }*/
        } else {
            System.out.println("No hay huéspedes que mostrar.");
        }
    }

    private void insertarHabitacion() {
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
            if (!(controlador.getHabitaciones().size() > 0)) {
                System.out.println("No hay habitaciones dadas de alta en el sistema.");
            } else {
                Habitacion habitacion = leerHabitacionPorIdentificador();
                Habitacion habitacion1 = controlador.buscar(habitacion);
                if (habitacion1 != null) {
                    System.out.println("La habitación buscada es: " + habitacion1);
                } else {
                    System.out.println("La habitación buscada no existe.");
                }
            }
        }catch(IllegalArgumentException | NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void borrarHabitacion() {
        String mensaje = "Borrar habitacción";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHabitaciones().size() > 0)) {
                System.out.println("No hay habitaciones dadas de alta en el sistema.");
            } else {
                Habitacion habitacion = leerHabitacionPorIdentificador();
                Habitacion habitacion1 = controlador.buscar(habitacion);
                if (habitacion1 == null) {
                    System.out.println("No existe la habitación indicada.");
                } else {
                    controlador.borrar(habitacion1);
                    System.out.println("Habitación borrada correctamente.");
                }
            }
        } catch (OperationNotSupportedException | IllegalArgumentException |
                 NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarHabitaciones(){
        String mensaje = "Mostrar habitaciones";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        List<Habitacion> listaHabitacion = controlador.getHabitaciones();
        if (listaHabitacion.size() > 0) {
            listaHabitacion.sort(Comparator.comparing(Habitacion::getIdentificador));
            listaHabitacion.forEach(habitacion1 -> System.out.println(habitacion1.toString()));
            /*for (Habitacion habitacion : listaHabitacion) {
                System.out.println(habitacion);
            }*/
        } else {
            System.out.println("No hay habitaciones que mostrar.");
        }
    }

    private void insertarReserva() {
        String mensaje = "Insertar reserva";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHabitaciones().size() > 0)) {
                System.out.println("No hay habitaciones dadas de alta en el sistema para poder realizar la reserva.");
            } else {
                String mensaje1 = "Consultar disponibilidad";
                System.out.printf("%n%s%n", mensaje1);
                String cadena1 = "%0" + mensaje1.length() + "d%n";
                System.out.println(String.format(cadena1, 0).replace("0", "-"));

                Habitacion habitacionDisponible = consultarDisponibilidad(
                        leerTipoHabitacion(),
                        leerFecha("Introduce la fecha de inicio de reserva (%s): "),
                        leerFecha("Introduce la fecha de fin de reserva (%s): "));
                if (habitacionDisponible == null) {
                    System.out.println("El tipo de habitación NO está disponible.");
                } else {
                    System.out.println("La habitación: " + habitacionDisponible + ", está disponible.");
                    Reserva reserva = leerReserva();
                    reserva.setHabitacion(habitacionDisponible);
                    controlador.insertar(reserva);
                    controlador.insertar(reserva.getHuesped());
                    System.out.println("Reserva insertada correctamente.");
                }
            }
        } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarReservas(Huesped huesped){
        String mensaje = "Listar reservas";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        List<Reserva> listaReservas = controlador.getReservas(huesped);
        if (listaReservas.size() > 0){
            listaReservas.sort(Comparator.comparing(
                    Reserva::getFechaInicioReserva).reversed().thenComparing(
                    (Reserva reserva1) -> reserva1.getHabitacion().getIdentificador()));
            int i = 0;
            for (Reserva reserva2 : listaReservas) {
                System.out.println(i + ".- " + reserva2);
                i++;
            }
        } else {
            System.out.println("No hay reservas que listar.");
        }
    }

    private void listarReservas(TipoHabitacion tipoHabitacion){
        String mensaje = "Listar reservas";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        List<Reserva> listaReservas  = controlador.getReservas(tipoHabitacion);
        if (listaReservas.size() > 0){
            listaReservas.sort(Comparator.comparing(
                    Reserva::getFechaInicioReserva).reversed().thenComparing(
                    (Reserva reserva1) -> reserva1.getHuesped().getNombre()));
            int i = 0;
            for (Reserva reserva2: listaReservas) {
                System.out.println(i + ".- " + reserva2);
                i++;
            }
        } else {
            System.out.println("No hay reservas que listar.");
        }
    }

    private List<Reserva> getReservasAnulables(List<Reserva> reservasAAnular){
        List<Reserva> reservasAnulables = new ArrayList<>();
        for (Reserva reserva2 : reservasAAnular) {
            if(reserva2.getFechaInicioReserva().isAfter(LocalDate.now())){
                reservasAnulables.add(new Reserva(reserva2));
            }
        }
        return reservasAnulables;
    }

    private void anularReserva() {
        String mensaje = "Anular reservas";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        char confReserva = 'S';
        try {
            if (!(controlador.getReservas().size() > 0)) {
                System.out.println("No hay reservas dadas de alta en el sistema.");
            } else {
                Huesped huesped = leerClientePorDni();
                Huesped huesped1 = controlador.buscar(huesped);
                if (huesped1 != null) {
                    List<Reserva> reservas = controlador.getReservas(huesped1);
                    if (reservas.size() > 0) {
                        reservas = getReservasAnulables(reservas);
                        int i = 0;
                        for (Reserva reserva2 : reservas) {
                            System.out.println(i + ".- " + reserva2);
                            i++;
                        }
                        System.out.println("Elija la reserva que desea anular.");
                        int numReserva = Entrada.entero();

                        if (reservas.size() == 1) {
                            System.out.println("Confirma que desea anular la reserva (S/N)?");
                            confReserva = Entrada.caracter();
                        }
                        if (confReserva == 'S') {
                            controlador.borrar(reservas.get(numReserva));
                            System.out.println("Reserva anulada correctamente.");
                        } else {
                            System.out.println("La reserva no ha sido anulada.");
                        }
                    } else {
                        System.out.println("No hay reservas que se puedan anular para dicho huésped.");
                    }
                } else {
                    System.out.println("No existe ningún huésped con dicho DNI.");
                }
            }
        } catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void mostrarReservas(){
        String mensaje = "Mostrar reservas";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        List<Reserva> listaReservas = controlador.getReservas();
        if (listaReservas.size() > 0) {
            listaReservas.sort(Comparator.comparing(
                    Reserva::getFechaInicioReserva).reversed().thenComparing(
                    (Reserva reserva1) -> reserva1.getHabitacion().getIdentificador()));
            listaReservas.forEach(reserva2 -> System.out.println(reserva2.toString()));

            /*for (Reserva reserva : listaReserva) {
                if (!(reserva == null))
                    System.out.println(reserva);
            }*/
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

            List<Habitacion> habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);

            if (!(habitacionesTipoSolicitado.size() > 0)) {
                return null;
            } else {
                for (Habitacion habitacion : habitacionesTipoSolicitado) {
                    List<Reserva> reservasFuturas = controlador.getReservaFuturas(habitacion);
                    int numElementosNoNulos = getNumElementosNoNulos(reservasFuturas);
                    if (numElementosNoNulos == 0) {
                        return habitacion;
                    } else {
                        /*Arrays.sort(reservasFuturas, 0, numElementosNoNulos,
                                Comparator.comparing(Reserva::getFechaFinReserva).reversed());
                        */
                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed());
                        if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                            return habitacion;
                        } else {
                            /*Arrays.sort(reservasFuturas, 0, numElementosNoNulos,
                                    Comparator.comparing(Reserva::getFechaInicioReserva));*/
                            reservasFuturas.sort(Comparator.comparing(Reserva::getFechaInicioReserva));
                            if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                                return habitacion;
                            } else {
                                Habitacion habitacionDisponible = null;
                                boolean tipoHabitacionEncontrada = false;
                                for (int j = 1; j < reservasFuturas.size() && !tipoHabitacionEncontrada; j++) {
                                    if (reservasFuturas.get(j) != null && reservasFuturas.get(j) != null) {
                                        if (fechaInicioReserva.isAfter(reservasFuturas.get(j).getFechaFinReserva()) &&
                                                fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {
                                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(j));
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

    private int getNumElementosNoNulos(List<Reserva> reservas) {
        //return (int) Arrays.stream(reservas).filter(Objects::nonNull).count();
        return reservas.size();
    }

    private void realizarCheckin() {
        String mensaje = "Realizar checkin";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHabitaciones().size() > 0)) {
                System.out.println("No hay habitaciones dadas de alta en el sistema. " +
                        "El checkin no se puede realizar.");
            } else {
                Huesped huesped = leerClientePorDni();
                Huesped huesped1 = controlador.buscar(huesped);
                if (!(huesped1 == null)) {
                    List<Reserva> reservas = controlador.getReservas(huesped1);
                    if (reservas.size() > 0) {
                        listarReservas(huesped1);
                        System.out.println("Elija la reserva a la que desea realizar el checkin.");
                        int numReserva = Entrada.entero();
                        Reserva reserva = reservas.get(numReserva);
                        listarReservas(reserva.getHabitacion().getTipoHabitacion());
                        String mensaje1 = "Introduce la fecha y hora de checkin de la reserva (%s): ";
                        LocalDateTime fechaHora = leerFechaHora(mensaje1);
                        controlador.realizarCheckin(reserva, fechaHora);
                        System.out.println("Checkin realizado correctamente.");
                    } else {
                        System.out.println("No existe ninguna reseva para dicho huésped.");
                    }
                } else {
                    System.out.println("No existe ningún huésped con dicho DNI.");
                }
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void realizarCheckOut() {
        String mensaje = "Realizar checkout";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d%n";
        System.out.println(String.format(cadena, 0).replace("0", "-"));

        try {
            if (!(controlador.getHabitaciones().size() > 0)) {
                System.out.println("No hay habitaciones dadas de alta en el sistema. " +
                        "El checkout no se puede realizar.");
            } else {
                Huesped huesped = leerClientePorDni();
                Huesped huesped1 = controlador.buscar(huesped);
                if (!(huesped1 == null)) {
                    List<Reserva> reservas = controlador.getReservas(huesped1);
                    if (reservas.size() > 0) {
                        listarReservas(huesped1);
                        System.out.println("Elija la reserva a la que desea realizar el checkin.");
                        int numReserva = Entrada.entero();
                        Reserva reserva = reservas.get(numReserva);
                        listarReservas(reserva.getHabitacion().getTipoHabitacion());
                        String mensaje1 = "Introduce la fecha y hora de checkin de la reserva (%s): ";
                        LocalDateTime fechaHora = leerFechaHora(mensaje1);
                        controlador.realizarCheckout(reserva, fechaHora);
                        System.out.println("Checkout realizado correctamente.");
                    } else {
                        System.out.println("No existe ninguna reseva para dicho huésped.");
                    }
                } else {
                    System.out.println("No existe ningún huésped con dicho DNI.");
                }
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}
