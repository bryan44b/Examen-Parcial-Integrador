package modelo.servicios;

public class Cirugia implements Facturable {

    private static final double TARIFA_HORA_QUIROFANO = 1500.0;

    private String tipoCirugia;
    private String complejidad;
    private double horasQuirofano;

    public Cirugia(String tipoCirugia, String complejidad, double horasQuirofano) {
        if (tipoCirugia == null || tipoCirugia.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cirugia no puede estar vacio.");
        }
        if (complejidad == null) {
            throw new IllegalArgumentException("La complejidad no puede estar vacia.");
        }
        String valor = complejidad.trim().toUpperCase();
        if (!valor.equals("BAJA") && !valor.equals("MEDIA") && !valor.equals("ALTA")) {
            throw new IllegalArgumentException("La complejidad debe ser BAJA, MEDIA o ALTA.");
        }
        if (horasQuirofano <= 0) {
            throw new IllegalArgumentException("Las horas de quirofano deben ser mayores a 0.");
        }
        this.tipoCirugia = tipoCirugia;
        this.complejidad = valor;
        this.horasQuirofano = horasQuirofano;
    }

    @Override
    public double calcularCosto() {
        double factor = 1.0;
        if (complejidad.equals("MEDIA")) {
            factor = 1.5;
        } else if (complejidad.equals("ALTA")) {
            factor = 2.0;
        }
        return horasQuirofano * TARIFA_HORA_QUIROFANO * factor;
    }

    @Override
    public String getDescripcion() {
        return "Cirugia " + tipoCirugia + " (complejidad " + complejidad + ")";
    }
}