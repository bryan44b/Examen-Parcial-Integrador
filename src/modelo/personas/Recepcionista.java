package modelo.personas;

public class Recepcionista extends Persona{

    private String turno;

    public Recepcionista(String nombre, String apellido, String telefono, String correo,
                         String turno) {
        super(nombre, apellido, telefono, correo);
        this.turno=turno;
    }
    public void setTurno(String turno) {
        if (turno == null || turno.trim().isEmpty()) {
            throw new IllegalArgumentException("El turno no puede estar vacio.");
        }
        this.turno = turno;
    }

    public String getTurno() {
        return this.turno;
    }


    @Override
    public String obtenerRol() {
        return "Recepcionista";
    }

    @Override
    public String toString() {
        return super.toString() + ", turno " + turno;
    }
}
