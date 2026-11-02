
package modelo.mascotas;

import modelo.personas.Dueno;

public class Perro extends Mascota {

    private String raza;
    private int nivelEnergia;

    public Perro(String nombre, int edad, double peso,
                 Dueno dueno, String raza, int nivelEnergia) {

        super(nombre, edad, peso, dueno);

        this.raza = raza;
        this.nivelEnergia = nivelEnergia;
    }

    @Override
    public String hacerSonido() {
        return "¡Guau guau!";
    }
}
