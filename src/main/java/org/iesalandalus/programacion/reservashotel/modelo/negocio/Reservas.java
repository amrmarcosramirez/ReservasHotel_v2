package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reservas {

    // Se crean los atributos con su visibilidad adecuada
    private int capacidad;
    private Reserva[] coleccionReservas;
    private int tamano;

    public  Reservas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        coleccionReservas = new Reserva[capacidad];
        tamano = 0;
    }

    public Reserva[] get() {
        return copiaProfundaReservas();
    }

    private Reserva[] copiaProfundaReservas() {
        Reserva[] copiaReservas = new Reserva[capacidad];
        for (int i = 0; !tamanoSuperado(i); i++) {
            copiaReservas[i] = new Reserva(coleccionReservas[i]);
        }
        return copiaReservas;
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
       Objects.requireNonNull(reserva, "ERROR: No se puede insertar una reserva nula.");
        int indice = buscarIndice(reserva);
        if (capacidadSuperada(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
        }
        if (tamanoSuperado(indice)) {
            coleccionReservas[indice] = new Reserva(reserva);
            tamano++;
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un reserva con el mismo nombre.");
        }

    }

    private int buscarIndice(Reserva reserva) {
        Objects.requireNonNull(reserva, "ERROR: No se puede buscar el índice de una reserva nula.");

        int indice = 0;
        boolean reservaEncontrada = false;
        while (!tamanoSuperado(indice) && !reservaEncontrada) {
            if (coleccionReservas[indice].equals(reserva)) {
                reservaEncontrada = true;
            } else {
                indice++;
            }
        }
        return indice;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= tamano;
    }

    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad;
    }

    public Reserva buscar(Reserva reserva) {
        Objects.requireNonNull(reserva, "ERROR: No se puede buscar una reserva nula.");

        int indice = buscarIndice(reserva);
        if (tamanoSuperado(indice)) {
            return null;
        } else {
            return new Reserva(coleccionReservas[indice]);
        }
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        Objects.requireNonNull(reserva, "ERROR: No se puede borrar una reserva nula.");

        int indice = buscarIndice(reserva);
        if (tamanoSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva con ese nombre.");
        } else {
            desplazarUnaPosicionHaciaIzquierda(indice);
        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        int i;
        for (i = indice; !tamanoSuperado(i); i++) {
            coleccionReservas[i] = coleccionReservas[i+1];
        }
        coleccionReservas[i] = null;
        tamano--;
    }

    public Reserva[] getReservas(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se pueden buscar reservas de un huésped nulo.");

        Reserva[] reservasHuesped = new Reserva[capacidad];
        int indice = 0;
        for (Reserva reserva : coleccionReservas) {
            if (reserva != null && reserva.getHuesped().equals(huesped)) {
                reservasHuesped[indice++] = new Reserva(reserva);
            }
        }
        return reservasHuesped;
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        Reserva[] reservasHabitacion = new Reserva[capacidad];
        int indice = 0;
        for (Reserva reserva : coleccionReservas) {
            if (reserva != null && reserva.getHabitacion().equals(tipoHabitacion)) {
                reservasHabitacion[indice++] = new Reserva(reserva);
            }
        }
        return reservasHabitacion;
    }

    public Reserva[] getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        Reserva[] reservasHabitacion = new Reserva[capacidad];
        int indice = 0;
        for (Reserva reserva : coleccionReservas) {
            if (reserva != null && reserva.getHabitacion().equals(habitacion)) {
                reservasHabitacion[indice++] = new Reserva(reserva);
            }
        }
        return reservasHabitacion;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reserva.setCheckIn(fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        reserva.setCheckOut(fecha);
    }

}
