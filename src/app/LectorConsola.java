package app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class LectorConsola {

    private Scanner scanner;

    public LectorConsola() {
        this.scanner = new Scanner(System.in);
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El dato no puede estar vacio.");
        }
    }

    public int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero entero valido.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero valido (por ejemplo 18.5).");
            }
        }
    }

    public boolean leerSiNo(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (texto.equalsIgnoreCase("s")) {
                return true;
            }
            if (texto.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Responde s o n.");
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no valida. Usa el formato aaaa-mm-dd.");
            }
        }
    }

    public LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDateTime.parse(texto.replace(" ", "T"));
            } catch (DateTimeParseException e) {
                System.out.println("Fecha u hora no valida. Usa el formato aaaa-mm-dd hh:mm.");
            }
        }
    }
}