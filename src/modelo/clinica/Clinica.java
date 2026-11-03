package modelo.clinica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.mascotas.Mascota;
import modelo.personas.Dueno;
import modelo.personas.Veterinario;
import modelo.servicios.Facturable;

public class Clinica {

    private String nombre;
    private ArrayList<Consultorio> consultorios;
    private ArrayList<Veterinario> veterinarios;
    private ArrayList<Dueno> duenos;
    private ArrayList<Mascota> mascotas;
    private ArrayList<Cita> citas;

    public Clinica(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la clinica no puede estar vacio.");
        }
        this.nombre = nombre;
        this.consultorios = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.duenos = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public Consultorio crearConsultorio(int numero) {
        if (buscarConsultorio(numero) != null) {
            throw new IllegalArgumentException("Ya existe el consultorio " + numero + ".");
        }
        Consultorio consultorio = new Consultorio(numero);
        consultorios.add(consultorio);
        return consultorio;
    }

    public void agregarVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario no puede ser nulo.");
        }
        for (Veterinario v : veterinarios) {
            if (v.getCedulaProfesional().equals(veterinario.getCedulaProfesional())) {
                throw new IllegalArgumentException("Ya hay un veterinario con esa cedula.");
            }
        }
        veterinarios.add(veterinario);
    }

    public void registrarDueno(Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException("El dueno no puede ser nulo.");
        }
        duenos.add(dueno);
    }

    public void registrarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula.");
        }
        mascotas.add(mascota);
    }

    public Cita agendarCita(Mascota mascota, Veterinario veterinario, int numeroConsultorio,
                            LocalDateTime fechaHora, Facturable servicio) {
        if (!mascotas.contains(mascota)) {
            throw new IllegalArgumentException("La mascota no esta registrada en la clinica.");
        }
        if (!veterinarios.contains(veterinario)) {
            throw new IllegalArgumentException("El veterinario no trabaja en esta clinica.");
        }
        Consultorio consultorio = buscarConsultorio(numeroConsultorio);
        if (consultorio == null) {
            throw new IllegalArgumentException("El consultorio " + numeroConsultorio
                    + " no existe.");
        }
        validarDisponibilidad(veterinario, consultorio, fechaHora);
        Cita cita = new Cita(mascota, veterinario, consultorio, fechaHora, servicio);
        citas.add(cita);
        return cita;
    }

    private void validarDisponibilidad(Veterinario veterinario, Consultorio consultorio,
                                       LocalDateTime fechaHora) {
        for (Cita c : citas) {
            if (!c.estaCancelada() && c.getFechaHora().equals(fechaHora)) {
                if (c.getVeterinario() == veterinario) {
                    throw new IllegalArgumentException("El veterinario ya tiene cita a esa hora.");
                }
                if (c.getConsultorio() == consultorio) {
                    throw new IllegalArgumentException("El consultorio ya esta ocupado.");
                }
            }
        }
    }

    private Consultorio buscarConsultorio(int numero) {
        for (Consultorio c : consultorios) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Cita> buscarCita(String nombreMascota) {
        ArrayList<Cita> encontradas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getMascota().getNombre().equalsIgnoreCase(nombreMascota)) {
                encontradas.add(c);
            }
        }
        return encontradas;
    }

    public ArrayList<Cita> buscarCita(LocalDate fecha) {
        ArrayList<Cita> encontradas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getFechaHora().toLocalDate().equals(fecha)) {
                encontradas.add(c);
            }
        }
        return encontradas;
    }

    public Comprobante generarComprobante(Cita cita) {
        return new Comprobante(cita);
    }

    public double totalCobradoPorVeterinario(Veterinario veterinario) {
        double total = 0;
        for (Cita c : citas) {
            if (c.estaAtendida() && c.getVeterinario() == veterinario) {
                total += c.getServicio().calcularCosto();
            }
        }
        return total;
    }

    public double totalCobradoPorMascota(Mascota mascota) {
        double total = 0;
        for (Cita c : citas) {
            if (c.estaAtendida() && c.getMascota() == mascota) {
                total += c.getServicio().calcularCosto();
            }
        }
        return total;
    }

    public double totalCobrado() {
        ArrayList<Facturable> servicios = new ArrayList<>();
        for (Cita c : citas) {
            if (c.estaAtendida()) {
                servicios.add(c.getServicio());
            }
        }
        double total = 0;
        for (Facturable s : servicios) {
            total += s.calcularCosto();
        }
        return total;
    }

    public void darDeBajaMascota(Mascota mascota) {
        if (!mascotas.contains(mascota)) {
            throw new IllegalArgumentException("La mascota no esta registrada en la clinica.");
        }
        for (int i = citas.size() - 1; i >= 0; i--) {
            if (citas.get(i).getMascota() == mascota) {
                citas.remove(i);
            }
        }
        mascotas.remove(mascota);
        // Ya nadie guarda una referencia a la mascota (ni la lista ni sus citas), asi que
        // el recolector de basura de Java libera su memoria junto con su historial clinico.
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Consultorio> getConsultorios() {
        return consultorios;
    }

    public ArrayList<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public ArrayList<Dueno> getDuenos() {
        return duenos;
    }

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }
}