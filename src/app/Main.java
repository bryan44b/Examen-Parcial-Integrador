package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.clinica.Cita;
import modelo.clinica.Clinica;
import modelo.mascotas.AveExotica;
import modelo.mascotas.Gato;
import modelo.mascotas.Mascota;
import modelo.mascotas.Perro;
import modelo.personas.Dueno;
import modelo.personas.Veterinario;
import modelo.servicios.Cirugia;
import modelo.servicios.ConsultaGeneral;
import modelo.servicios.Facturable;
import modelo.servicios.Vacunacion;

public class Main {

    private Clinica clinica;
    private LectorConsola lector;

    public Main() {
        this.clinica = new Clinica("PetCare");
        this.lector = new LectorConsola();
        clinica.crearConsultorio(1);
        clinica.crearConsultorio(2);
        clinica.crearConsultorio(3);
    }

    public static void main(String[] args) {
        Main programa = new Main();
        programa.iniciar();
    }

    private void iniciar() {
        int opcion = 0;
        while (opcion != 10) {
            mostrarMenu();
            opcion = lector.leerEntero("Elige una opcion: ");
            try {
                ejecutarOpcion(opcion);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== PetCare =====");
        System.out.println("1. Registrar dueno y mascota");
        System.out.println("2. Registrar veterinario");
        System.out.println("3. Agendar cita");
        System.out.println("4. Atender o cancelar cita");
        System.out.println("5. Generar comprobante");
        System.out.println("6. Consultar historial clinico");
        System.out.println("7. Listar mascotas y su sonido");
        System.out.println("8. Ver total cobrado");
        System.out.println("9. Dar de baja una mascota");
        System.out.println("10. Salir");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarDuenoYMascota();
            case 2 -> registrarVeterinario();
            case 3 -> agendarCita();
            case 4 -> atenderOCancelarCita();
            case 5 -> generarComprobante();
            case 6 -> consultarHistorial();
            case 7 -> listarMascotas();
            case 8 -> mostrarTotales();
            case 9 -> darDeBajaMascota();
            case 10 -> System.out.println("Saliendo...");
            default -> System.out.println("Opcion no valida. Elige un numero del 1 al 10.");
        }
    }

    private void registrarDuenoYMascota() {
        Dueno dueno = pedirDueno();
        Mascota mascota = crearMascota(dueno);
        if (!clinica.getDuenos().contains(dueno)) {
            clinica.registrarDueno(dueno);
        }
        clinica.registrarMascota(mascota);
        System.out.println("Registrado: " + mascota.getNombre() + ", mascota de "
                + dueno.getNombreCompleto());
    }

    private Dueno pedirDueno() {
        if (!clinica.getDuenos().isEmpty()
                && lector.leerSiNo("Usar un dueno ya registrado? (s/n): ")) {
            return elegirDueno();
        }
        String nombre = lector.leerTexto("Nombre del dueno: ");
        String apellido = lector.leerTexto("Apellido: ");
        String telefono = lector.leerTexto("Telefono: ");
        String correo = lector.leerTexto("Correo: ");
        String direccion = lector.leerTexto("Direccion: ");
        return new Dueno(nombre, apellido, telefono, correo, direccion);
    }

    private Mascota crearMascota(Dueno dueno) {
        String nombre = lector.leerTexto("Nombre de la mascota: ");
        int edad = lector.leerEntero("Edad (anios): ");
        double peso = lector.leerDecimal("Peso (kg): ");
        System.out.println("Tipo: 1. Perro  2. Gato  3. Ave exotica");
        int tipo = lector.leerEntero("Elige el tipo: ");
        if (tipo == 1) {
            String raza = lector.leerTexto("Raza: ");
            int energia = lector.leerEntero("Nivel de energia (1 a 5): ");
            return new Perro(nombre, edad, peso, dueno, raza, energia);
        }
        if (tipo == 2) {
            boolean domestico = lector.leerSiNo("Es domestico? (s/n): ");
            return new Gato(nombre, edad, peso, dueno, domestico);
        }
        if (tipo == 3) {
            double alas = lector.leerDecimal("Longitud de alas (cm): ");
            return new AveExotica(nombre, edad, peso, dueno, alas);
        }
        throw new IllegalArgumentException("Tipo de mascota no valido.");
    }

    private void registrarVeterinario() {
        String nombre = lector.leerTexto("Nombre: ");
        String apellido = lector.leerTexto("Apellido: ");
        String telefono = lector.leerTexto("Telefono: ");
        String correo = lector.leerTexto("Correo: ");
        String cedula = lector.leerTexto("Cedula profesional: ");
        String especialidad = lector.leerTexto("Especialidad: ");
        Veterinario veterinario = new Veterinario(nombre, apellido, telefono, correo,
                cedula, especialidad);
        clinica.agregarVeterinario(veterinario);
        System.out.println("Veterinario registrado: " + veterinario.getNombreCompleto());
    }

    private void agendarCita() {
        Mascota mascota = elegirMascota();
        Veterinario veterinario = elegirVeterinario();
        int consultorio = lector.leerEntero("Numero de consultorio (1 a 3): ");
        LocalDateTime fechaHora = lector.leerFechaHora("Fecha y hora (aaaa-mm-dd hh:mm): ");
        Facturable servicio = pedirServicio();
        Cita cita = clinica.agendarCita(mascota, veterinario, consultorio, fechaHora, servicio);
        System.out.println("Cita agendada: " + cita);
        System.out.println("Costo del servicio: $" + formatear(servicio.calcularCosto()));
    }

    private Facturable pedirServicio() {
        System.out.println("Servicio: 1. Consulta general  2. Vacunacion  3. Cirugia");
        int tipo = lector.leerEntero("Elige el servicio: ");
        if (tipo == 1) {
            return new ConsultaGeneral(lector.leerTexto("Motivo de la consulta: "));
        }
        if (tipo == 2) {
            String vacuna = lector.leerTexto("Nombre de la vacuna: ");
            double costo = lector.leerDecimal("Costo de la vacuna: ");
            return new Vacunacion(vacuna, costo);
        }
        if (tipo == 3) {
            String cirugia = lector.leerTexto("Tipo de cirugia: ");
            String complejidad = lector.leerTexto("Complejidad (BAJA, MEDIA o ALTA): ");
            double horas = lector.leerDecimal("Horas de quirofano: ");
            return new Cirugia(cirugia, complejidad, horas);
        }
        throw new IllegalArgumentException("Servicio no valido.");
    }

    private void atenderOCancelarCita() {
        Cita cita = elegirCita(buscarCitas());
        System.out.println("1. Atender  2. Cancelar");
        int accion = lector.leerEntero("Elige la accion: ");
        if (accion == 1) {
            cita.atender();
            double costo = cita.getServicio().calcularCosto();
            System.out.println("Cita atendida. Costo: $" + formatear(costo));
        } else if (accion == 2) {
            cita.cancelar();
            System.out.println("Cita cancelada.");
        } else {
            System.out.println("Accion no valida.");
        }
    }

    private ArrayList<Cita> buscarCitas() {
        System.out.println("Ver citas: 1. Por mascota  2. Por fecha  3. Todas");
        int modo = lector.leerEntero("Elige una forma de buscar: ");
        if (modo == 1) {
            return clinica.buscarCita(lector.leerTexto("Nombre de la mascota: "));
        }
        if (modo == 2) {
            return clinica.buscarCita(lector.leerFecha("Fecha (aaaa-mm-dd): "));
        }
        if (modo == 3) {
            return clinica.getCitas();
        }
        throw new IllegalArgumentException("Forma de buscar no valida.");
    }

    private void generarComprobante() {
        ArrayList<Cita> atendidas = new ArrayList<>();
        for (Cita c : clinica.getCitas()) {
            if (c.estaAtendida()) {
                atendidas.add(c);
            }
        }
        Cita cita = elegirCita(atendidas);
        System.out.println(clinica.generarComprobante(cita));
    }

    private void consultarHistorial() {
        Mascota mascota = elegirMascota();
        System.out.println("Historial clinico de " + mascota.getNombre() + ":");
        mascota.getHistorial().mostrar();
    }

    private void listarMascotas() {
        if (clinica.getMascotas().isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        }
        for (Mascota m : clinica.getMascotas()) {
            System.out.println(m.getNombre() + " dice: " + m.hacerSonido());
        }
    }

    private void mostrarTotales() {
        System.out.println("Total cobrado por veterinario:");
        for (Veterinario v : clinica.getVeterinarios()) {
            double total = clinica.totalCobradoPorVeterinario(v);
            System.out.println(" - " + v.getNombreCompleto() + ": $" + formatear(total));
        }
        System.out.println("Total cobrado por mascota:");
        for (Mascota m : clinica.getMascotas()) {
            double total = clinica.totalCobradoPorMascota(m);
            System.out.println(" - " + m.getNombre() + ": $" + formatear(total));
        }
        System.out.println("Total general: $" + formatear(clinica.totalCobrado()));
    }

    private void darDeBajaMascota() {
        Mascota mascota = elegirMascota();
        clinica.darDeBajaMascota(mascota);
        System.out.println(mascota.getNombre() + " fue dada de baja junto con sus citas.");
    }

    private Mascota elegirMascota() {
        ArrayList<Mascota> mascotas = clinica.getMascotas();
        if (mascotas.isEmpty()) {
            throw new IllegalArgumentException("No hay mascotas registradas.");
        }
        for (int i = 0; i < mascotas.size(); i++) {
            Mascota m = mascotas.get(i);
            System.out.println((i + 1) + ". " + m.getNombre() + " (dueno: "
                    + m.getDueno().getNombreCompleto() + ")");
        }
        int numero = lector.leerEntero("Numero de la mascota: ");
        validarNumero(numero, mascotas.size());
        return mascotas.get(numero - 1);
    }

    private Dueno elegirDueno() {
        ArrayList<Dueno> duenos = clinica.getDuenos();
        for (int i = 0; i < duenos.size(); i++) {
            System.out.println((i + 1) + ". " + duenos.get(i).getNombreCompleto());
        }
        int numero = lector.leerEntero("Numero del dueno: ");
        validarNumero(numero, duenos.size());
        return duenos.get(numero - 1);
    }

    private Veterinario elegirVeterinario() {
        ArrayList<Veterinario> veterinarios = clinica.getVeterinarios();
        if (veterinarios.isEmpty()) {
            throw new IllegalArgumentException("No hay veterinarios registrados.");
        }
        for (int i = 0; i < veterinarios.size(); i++) {
            Veterinario v = veterinarios.get(i);
            System.out.println((i + 1) + ". " + v.getNombreCompleto() + " ("
                    + v.getEspecialidad() + ")");
        }
        int numero = lector.leerEntero("Numero del veterinario: ");
        validarNumero(numero, veterinarios.size());
        return veterinarios.get(numero - 1);
    }

    private Cita elegirCita(ArrayList<Cita> citas) {
        if (citas.isEmpty()) {
            throw new IllegalArgumentException("No hay citas en esa lista.");
        }
        for (int i = 0; i < citas.size(); i++) {
            System.out.println((i + 1) + ". " + citas.get(i));
        }
        int numero = lector.leerEntero("Numero de la cita: ");
        validarNumero(numero, citas.size());
        return citas.get(numero - 1);
    }

    private void validarNumero(int numero, int cantidad) {
        if (numero < 1 || numero > cantidad) {
            throw new IllegalArgumentException("Ese numero no esta en la lista.");
        }
    }

    private String formatear(double monto) {
        return String.format("%.2f", monto);
    }
}