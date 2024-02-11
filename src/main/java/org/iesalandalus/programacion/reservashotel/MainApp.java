package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.Vista;


public class MainApp {

    // Main
    public static void main(String[] args) {
        //try {
            System.out.println("Programa para la gesti�n de reservas del hotel IES Al-�ndalus");
            Modelo modelo = new Modelo();
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo, vista);
            controlador.comenzar();
        /*} catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
