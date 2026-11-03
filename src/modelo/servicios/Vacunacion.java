package modelo.servicios;

public class Vacunacion implements Facturable {

    private static final double TARIFA_APLICACION = 100.0;

    private String nombreVacuna;
    private double costoVacuna;

    public Vacunacion(String nombreVacuna, double costoVacuna) {
        if (nombreVacuna == null || nombreVacuna.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacio.");
        }
        if (costoVacuna < 0) {
            throw new IllegalArgumentException("El costo de la vacuna no puede ser negativo.");
        }
        this.nombreVacuna = nombreVacuna;
        this.costoVacuna = costoVacuna;
    }

    @Override
    public double calcularCosto() {
        return TARIFA_APLICACION + costoVacuna;
    }

    @Override
    public String getDescripcion() {
        return "Vacunacion (" + nombreVacuna + ")";
    }
}