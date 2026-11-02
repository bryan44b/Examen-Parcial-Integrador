
package modelo.mascotas;

import java.util.ArrayList;
import java.util.List;

public class HistorialClinico {

    private List<String> registros;

    // Constructor
    HistorialClinico() {
        this.registros = new ArrayList<>();
    }

    public void registrarConsulta(String descripcion) {

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripcion no puede estar vacia."
            );
        }

        registros.add(descripcion);
    }

    public void mostrar() {

        if (registros.isEmpty()) {
            System.out.println("No hay registros clinicos.");
            return;
        }

        for (String registro : registros) {
            System.out.println(registro);
        }
    }
}
