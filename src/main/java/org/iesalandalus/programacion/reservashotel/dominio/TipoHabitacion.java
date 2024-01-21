package org.iesalandalus.programacion.reservashotel.dominio;

public enum TipoHabitacion {
    SUITE("SUITE", 4),
    SIMPLE("SIMPLE", 1),
    DOBLE("DOBLE", 2),
    TRIPLE("TRIPLE", 3);

    private String descripcion;
    private int numeroMaximoPersonas;

    TipoHabitacion(String descripcion, int numeroMaximoPersonas) {
        this.descripcion = descripcion;
        this.numeroMaximoPersonas = numeroMaximoPersonas;
    }

    public int getNumeroMaximoPersonas() {
        return numeroMaximoPersonas;
    }

    @Override
    public String toString() {
        return String.format("TipoHabitacion[descripción=%s, número personas=%s]",
                this.descripcion, getNumeroMaximoPersonas());
    }
}
