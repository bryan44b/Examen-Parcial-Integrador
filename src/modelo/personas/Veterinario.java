package modelo.personas;

public class Veterinario extends Persona {

    private String cedulaProfesional;
    private String especialidad;

    public Veterinario(String nombre, String apellido, String telefono, String correo,
                       String cedulaProfesional, String especialidad) {
        super(nombre, apellido, telefono, correo);
        this.cedulaProfesional=cedulaProfesional;
        this.especialidad=especialidad;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        if (cedulaProfesional == null || cedulaProfesional.trim().isEmpty()) {
            throw new IllegalArgumentException("La cedula profesional no puede estar vacia.");
        }
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getCedulaProfesional() {
        return this.cedulaProfesional;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacia.");
        }
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }



    @Override
    public String obtenerRol() {
        return "Veterinario";
    }

    @Override
    public String toString() {
        return super.toString() + ", cedula profesional " + cedulaProfesional
                + ", especialidad " + especialidad;
    }
}
