package org.iesalandalus.programacion.reservashotel.dominio;

public enum TipoHabitacion {
    SUITE("SUITE", 4),
    SIMPLE("SIMPLE", 1),
    DOBLE("DOBLE", 2),
    TRIPLE("TRIPLE", 3);

    // Se crean los atributos con su visibilidad adecuada
    private String descripcion;
    private int numeroMaximoPersonas;

    //Constructor
    TipoHabitacion(String descripcion, int numeroMaximoPersonas) {
        this.descripcion = descripcion;
        this.numeroMaximoPersonas = numeroMaximoPersonas;
    }

    //Método de acceso
    public int getNumeroMaximoPersonas() {
        return numeroMaximoPersonas;
    }

    //Método toString
    @Override
    public String toString() {
        return String.format("Tipo Habitacion: %s, número personas: %s",
                this.descripcion, getNumeroMaximoPersonas());
    }
}
