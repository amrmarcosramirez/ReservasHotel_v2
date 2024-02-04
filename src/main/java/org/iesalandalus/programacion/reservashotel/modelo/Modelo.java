package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Reservas;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;
import static org.iesalandalus.programacion.reservashotel.vista.Consola.leerClientePorDni;

public class Modelo {
    private static final int CAPACIDAD = 45;
    private Habitaciones habitaciones;
    private Reservas reservas;
    private Huespedes huespedes;

    //Se crea el constructor copia vacio
    public Modelo() {}

    //Se crean los métodos
    public void comenzar() {
        this.habitaciones = new Habitaciones(CAPACIDAD);
        this.reservas = new Reservas(CAPACIDAD);
        this.huespedes = new Huespedes(CAPACIDAD);
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.!!!");
    }

    private void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    private Huesped buscar(Huesped huesped) {
        return huespedes.buscar(huesped);
    }

    private void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    private Huesped[] getHuespedes() {
        return huespedes.get();
    }

    private void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    private Habitacion buscar(Habitacion habitacion) {
        return habitaciones.buscar(habitacion);
    }

    private void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    private Habitacion[] getHabitaciones() {
        return habitaciones.get();
    }

    private Habitacion[] getHabitaciones(TipoHabitacion tipoHabitacion) {
        Habitacion[] listaHabitaciones = null;
        int j = 0;
        for (Habitacion habitacion : habitaciones.get()){
            if(habitacion.getTipoHabitacion().equals(tipoHabitacion)) {
                listaHabitaciones[j] = new Habitacion(habitacion);
                j++;
            }
        }
        return listaHabitaciones;
    }

    private void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    private Reserva buscar(Reserva reserva) {
        return reservas.buscar(reserva);
    }

    private void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    private Reserva[] getReservas() {
        return reservas.get();
    }

    private Reserva[] getReservas(Huesped huesped) {
        Reserva[] listaReservas = new Reserva[reservas.getReservas(huesped).length];
        int i = 0;
        for (Reserva reserva : reservas.getReservas(huesped)){
            listaReservas[i] = new Reserva(reserva);
            i++;
        }
        return listaReservas;
    }

    private Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        Reserva[] listaReservas;
        listaReservas = new Reserva[reservas.getReservas(tipoHabitacion).length];
        int j = 0;
        for (Reserva reserva : reservas.getReservas(tipoHabitacion)){
            listaReservas[j] = new Reserva(reserva);
            j++;
        }
        return listaReservas;
    }

    private Reserva[] getReservasFuturas(Habitacion habitacion) {
        Reserva[] listaHabitacionesFuturas;
        listaHabitacionesFuturas = new Reserva[reservas.getReservasFuturas(habitacion).length];
        int j = 0;
        for (Reserva reserva : reservas.getReservasFuturas(habitacion)){
            listaHabitacionesFuturas[j] = new Reserva(reserva);
            j++;
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
