package modelo.personas;

public class Dueno extends Persona {

    private String direccion;

    public Dueno() {
        super("Juan", "Martinez Cuarta", "0000000", "JuanitoMarCu@gmail.com");
        this.direccion = "Sin direccion";
    }

    public Dueno(String nombre, String apellido, String telefono, String correo,
                 String direccion) {
        super(nombre, apellido, telefono, correo);
        this.direccion = direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La direccion no puede estar vacia.");
        }
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String obtenerRol() {
        return "Dueno";
    }

    @Override
    public String toString() {
        return super.toString() + ", direccion " + direccion;
    }
}