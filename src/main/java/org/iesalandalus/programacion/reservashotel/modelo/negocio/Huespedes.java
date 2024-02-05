package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Huespedes {

    // Se crean los atributos con su visibilidad adecuada
    private int capacidad;
    private int tamano;
    private Huesped[] coleccionHuespedes;

    //Constructores
    public Huespedes(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        coleccionHuespedes = new Huesped[capacidad];
        tamano = 0;
    }

    //Métodos de acceso y modificación
    public Huesped[] get() {
        return copiaProfundaHuespedes();
    }

    private Huesped[] copiaProfundaHuespedes() {
        Huesped[] copiaHuespedes = new Huesped[capacidad];
        for (int i = 0; !tamanoSuperado(i); i++) {
            copiaHuespedes[i] = new Huesped(coleccionHuespedes[i]);
        }
        return copiaHuespedes;
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede insertar un huésped nulo.");
        int indice = buscarIndice(huesped);
        if (capacidadSuperada(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más huéspedes.");
        }
        if (tamanoSuperado(indice)) {
            coleccionHuespedes[indice] = new Huesped(huesped);
            tamano++;
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
    }

    private int buscarIndice(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se puede buscar el índice de un huésped nulo.");

        int indice = 0;
        boolean huespedEncontrado = false;
        while (!tamanoSuperado(indice) && !huespedEncontrado) {
            if (huesped.equals(get()[indice])) {
                huespedEncontrado = true;
            } else {
                indice++;
            }
        }
        return indice;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= getTamano();
    }

    private boolean capacidadSuperada(int indice) {
        return indice >= getCapacidad();
    }

    public Huesped buscar(Huesped huesped) {

        int indice = buscarIndice(huesped);
        if (tamanoSuperado(indice)) {
            return null;
        } else {
            return new Huesped(get()[indice]);
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede borrar un huésped nulo.");

        int indice = buscarIndice(huesped);
        if (tamanoSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        } else {
            desplazarUnaPosicionHaciaIzquierda(indice);
        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        int i;
        for (i = indice; !tamanoSuperado(i); i++) {
            coleccionHuespedes[i] = coleccionHuespedes[i+1];
        }
        coleccionHuespedes[i] = null;
        tamano--;
    }

}
