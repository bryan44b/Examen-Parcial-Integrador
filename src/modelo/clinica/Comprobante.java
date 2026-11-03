package modelo.clinica;

public class Comprobante {

    private static int contadorComprobantes = 0;

    private int numero;
    private Cita cita;
    private double total;

    public Comprobante(Cita cita) {
        if (cita == null || !cita.estaAtendida()) {
            throw new IllegalArgumentException("Solo se cobra una cita atendida.");
        }
        double costo = cita.getServicio().calcularCosto();
        if (costo < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo.");
        }
        contadorComprobantes++;
        this.numero = contadorComprobantes;
        this.cita = cita;
        this.total = costo;
    }

    public static int getContadorComprobantes() {
        return contadorComprobantes;
    }

    public int getNumero() {
        return numero;
    }

    public Cita getCita() {
        return cita;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Comprobante No. " + numero + "\n"
                + "Mascota: " + cita.getMascota().getNombre() + "\n"
                + "Veterinario: " + cita.getVeterinario().getNombreCompleto() + "\n"
                + "Servicio: " + cita.getServicio().getDescripcion() + "\n"
                + "Total: $" + String.format("%.2f", total);
    }
}