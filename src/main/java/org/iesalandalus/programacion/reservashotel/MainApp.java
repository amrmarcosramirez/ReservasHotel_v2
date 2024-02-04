package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;

import static org.iesalandalus.programacion.reservashotel.vista.Consola.*;

public class MainApp {

    // Main
    public static void main(String[] args) {

        System.out.println("Programa para la gestión de reservas del hotel IES Al-Ándalus");
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }
}
