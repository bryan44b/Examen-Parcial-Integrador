
package modelo.mascotas;

import modelo.personas.Dueno;
import modelo.personas.Persona;

public abstract class Mascota {

    private String nombre;
    private int edad;
    private double peso;
    private Dueno dueno;
    private HistorialClinico historial;

    public Mascota(String nombre, int edad, double peso, Dueno dueno) {
        setNombre(nombre);
        setEdad(edad);
        setPeso(peso);
        setDueno(dueno);
        this.historial = new HistorialClinico();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio."
            );
        }
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        if (edad <= 0) {
            throw new IllegalArgumentException(
                    "La edad debe ser mayor a 0."
            );
        }
        this.edad = edad;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor a 0."
            );
        }
        this.peso = peso;
    }

    public void setDueno(Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException(
                    "La mascota debe tener un dueno."
            );
        }
        this.dueno = dueno;
    }

    public abstract String hacerSonido();

    public String getNombre() {
        return this.nombre;
    }

    public HistorialClinico getHistorial() {
        return this.historial;
    }

    public Persona getDueno() {
        return this.dueno;
    }
}
