package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Reservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;

public class Modelo {

    // Se crean los atributos con su visibilidad adecuada
    private static final int CAPACIDAD = 45;
    private Habitaciones habitaciones;
    private Reservas reservas;
    private Huespedes huespedes;

    //Se crea el constructor
    public Modelo() {}

    //Se crean los métodos
    public void comenzar() {
        this.habitaciones = new Habitaciones(CAPACIDAD);
        this.reservas = new Reservas(CAPACIDAD);
        this.huespedes = new Huespedes(CAPACIDAD);
    }

    public void terminar() {
        System.out.println("Hasta luego.!!!");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public Huesped[] getHuespedes() {
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public Habitacion[] getHabitaciones() {
        return habitaciones.get();
    }

    public Habitacion[] getHabitaciones(TipoHabitacion tipoHabitacion) {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public Reserva buscar(Reserva reserva) {
        return reservas.buscar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public Reserva[] getReservas() {
        return reservas.get();
    }

    public Reserva[] getReservas(Huesped huesped) {
        Reserva[] listaReservas = new Reserva[reservas.getReservas(huesped).length];
        int i = 0;
        for (Reserva reserva : reservas.getReservas(huesped)){
            listaReservas[i++] = new Reserva(reserva);
        }
        return listaReservas;
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        Reserva[] listaReservas;
        listaReservas = new Reserva[reservas.getReservas(tipoHabitacion).length];
        int j = 0;
        for (Reserva reserva : reservas.getReservas(tipoHabitacion)){
            listaReservas[j++] = new Reserva(reserva);
        }
        return listaReservas;
    }

    public Reserva[] getReservasFuturas(Habitacion habitacion) {
        Reserva[] listaHabitacionesFuturas;
        listaHabitacionesFuturas = new Reserva[reservas.getReservasFuturas(habitacion).length];
        int j = 0;
        for (Reserva reserva : reservas.getReservasFuturas(habitacion)){
            listaHabitacionesFuturas[j++] = new Reserva(reserva);
        }
        return listaHabitacionesFuturas;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckin(reserva, fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckOut(reserva, fecha);
    }

}
