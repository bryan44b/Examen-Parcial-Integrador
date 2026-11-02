
package modelo.mascotas;

import modelo.personas.Dueno;

public class Gato extends Mascota {

    private boolean esDomestico;

    public Gato(String nombre, int edad, double peso,
                Dueno dueno, boolean esDomestico) {

        super(nombre, edad, peso, dueno);

        this.esDomestico = esDomestico;
    }

    @Override
    public String hacerSonido() {
        return "¡Miau!";
    }
}
