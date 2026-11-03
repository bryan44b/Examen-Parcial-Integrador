package modelo.servicios;

public class ConsultaGeneral implements Facturable {

    private static final double TARIFA_BASE = 350.0;

    private String motivo;

    public ConsultaGeneral(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de la consulta no puede estar vacio.");
        }
        this.motivo = motivo;
    }

    @Override
    public double calcularCosto() {
        return TARIFA_BASE;
    }

    @Override
    public String getDescripcion() {
        return "Consulta general (" + motivo + ")";
    }
}