package modelo.clinica;

public class Consultorio {

    private int numero;

    // Acceso de paquete (sin public): solo Clinica, que esta en este mismo paquete,
    // puede crear consultorios, porque le pertenecen y desaparecen con ella.
    Consultorio(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de consultorio debe ser mayor que cero.");
        }
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Consultorio " + numero;
    }
}