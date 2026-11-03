package modelo.clinica;

import java.time.LocalDateTime;
import modelo.mascotas.Mascota;
import modelo.personas.Veterinario;
import modelo.servicios.Facturable;

public class Cita {

    private Mascota mascota;
    private Veterinario veterinario;
    private Consultorio consultorio;
    private LocalDateTime fechaHora;
    private Facturable servicio;
    private String estado;

    public Cita(Mascota mascota, Veterinario veterinario, Consultorio consultorio,
                LocalDateTime fechaHora, Facturable servicio) {
        if (mascota == null || veterinario == null || consultorio == null
                || fechaHora == null || servicio == null) {
            throw new IllegalArgumentException("La cita necesita todos sus datos.");
        }
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.consultorio = consultorio;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.estado = "PROGRAMADA";
    }

    public void atender() {
        if (estaCancelada()) {
            throw new IllegalArgumentException("Una cita cancelada no se puede atender.");
        }
        if (estaAtendida()) {
            throw new IllegalArgumentException("La cita ya fue atendida.");
        }
        this.estado = "ATENDIDA";
        String registro = fechaHora.toString().replace("T", " ") + " | "
                + servicio.getDescripcion() + " | " + veterinario.getNombreCompleto()
                + " | $" + servicio.calcularCosto();
        mascota.getHistorial().registrarConsulta(registro);
    }

    public void cancelar() {
        if (estaAtendida()) {
            throw new IllegalArgumentException("Una cita atendida no puede cancelarse.");
        }
        this.estado = "CANCELADA";
    }

    public boolean estaProgramada() {
        return estado.equals("PROGRAMADA");
    }

    public boolean estaAtendida() {
        return estado.equals("ATENDIDA");
    }

    public boolean estaCancelada() {
        return estado.equals("CANCELADA");
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Facturable getServicio() {
        return servicio;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return mascota.getNombre() + " con " + veterinario.getNombreCompleto() + ", "
                + consultorio + ", " + fechaHora.toString().replace("T", " ") + ", "
                + servicio.getDescripcion() + ", estado " + estado;
    }
}
