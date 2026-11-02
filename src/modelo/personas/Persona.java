package modelo.personas;

public abstract class Persona {

    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public abstract String obtenerRol();

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio.");
        }
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono no puede estar vacio.");
        }
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("El correo debe contener @.");
        }
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (" + obtenerRol() + "), tel. " + telefono;
    }
}