# Modelo de análisis: PetCare

## 1. Sustantivos y verbos del caso de estudio

**Clases o atributos**: persona, dueño, veterinario,
recepcionista, mascota, perro, gato, ave exótica, historial clínico, clínica, consultorio,
cita, servicio, consulta general, vacunación, cirugía, comprobante, nombre, apellido,
teléfono, correo, dirección, cédula, especialidad, turno, edad, peso, raza, nivel de
energía, longitud de alas, fecha y hora, costo, total.

**Verbos** (candidatos a métodos): registrar, agendar, atender, cancelar, calcular costo,
generar comprobante, consultar historial, emitir sonido, dar de baja, asignar veterinario a
la clínica, listar.

## 2. Tabla de clases y objetos


| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
|---|---|---|---|---|
| `Persona` | Abstracta | `nombre`, `apellido`, `telefono`, `correo` (String) | `getNombreCompleto()`, `obtenerRol()` (abstracto), setters con validación | Reúne los datos comunes de dueño, veterinario y recepcionista. Es abstracta porque no existe una "persona" genérica en la clínica. |
| `Dueno` | Concreta | `direccion: String` | `Dueno()` por defecto, `Dueno(todos los datos)`, `obtenerRol()` | Persona dueña de las mascotas. |
| `Veterinario` | Concreta | `cedulaProfesional`, `especialidad` (String) | constructor con `super(...)`, `obtenerRol()` | Atiende las citas. Existe aunque la clínica cierre, por eso la clínica lo recibe ya creado. |
| `Recepcionista` | Concreta | `turno: String` | constructor con `super(...)`, `obtenerRol()` | Completa la jerarquía de `Persona`. |
| `Mascota` | Abstracta | `nombre`, `edad: int`, `peso: double`, `dueno: Dueno`, `historial: HistorialClinico` | `hacerSonido()` (abstracto), `setEdad(int)` y `setPeso(double)` (validan > 0) | Reúne lo común a todos los animales. Crea su propio historial al construirse. |
| `Perro` | Concreta | `raza: String`, `nivelEnergia: int` | constructor con `super(...)`, `hacerSonido()` | Primer tipo de mascota. |
| `Gato` | Concreta | `esDomestico: boolean` | constructor con `super(...)`, `hacerSonido()` | Segundo tipo de mascota. |
| `AveExotica` | Concreta | `longitudAlas: double` | constructor con `super(...)`, `hacerSonido()` | Tercer tipo de mascota. |
| `HistorialClinico` | Concreta | `registros: List<String>` | `registrarConsulta(String)`, `mostrar()` | No tiene sentido sin su mascota: la mascota lo crea y desaparece con ella. |
| `Facturable` | Interfaz | (ninguno) | `calcularCosto(): double`, `getDescripcion(): String` | Contrato de todo lo que se cobra; permite sumar costos con polimorfismo, sin `instanceof`. |
| `ConsultaGeneral` | Concreta (implementa `Facturable`) | `TARIFA_BASE` (static final), `motivo` | `calcularCosto()` devuelve la tarifa base | Servicio de costo fijo. |
| `Vacunacion` | Concreta (implementa `Facturable`) | `nombreVacuna`, `costoVacuna` | `calcularCosto()` suma el costo de la vacuna a la aplicación | Servicio cuyo costo incluye la vacuna. |
| `Cirugia` | Concreta (implementa `Facturable`) | `complejidad`, `horasQuirofano` | `calcularCosto()` según complejidad y horas | Servicio con el cálculo más complejo. |
| `Cita` | Concreta | `mascota`, `veterinario`, `consultorio`, `fechaHora`, `servicio: Facturable`, `estado` | `atender()`, `cancelar()` | Relaciona objetos independientes. Concentra el cambio de estado y la regla de que una cita cancelada no se atiende. |
| `Comprobante` | Concreta | `contadorComprobantes` (static), `numero`, `cita`, `total` | `Comprobante(Cita)` | Registro del cobro de una cita atendida. El contador estático da el número consecutivo y valida que el total no sea negativo. |
| `Consultorio` | Concreta | `numero: int` | `Consultorio(int)` (acceso de paquete) | Pertenece a la clínica: solo ella lo crea y desaparece con ella. |
| `Clinica` | Concreta | `nombre`, `consultorios`, `veterinarios`, `mascotas`, `citas` | `agregarVeterinario`, `agendarCita` (reglas de horario y consultorio), `buscarCita(String)` y `buscarCita(LocalDate)`, `generarComprobante`, `totalCobradoPorVeterinario`, `darDeBajaMascota` | Punto de entrada del dominio: coordina las colecciones y las reglas que involucran a varios objetos. |

### Clases de apoyo

`Main` (menú de consola), `LectorConsola` (lectura y validación de entradas) y las
enumeraciones `EstadoCita`, `Complejidad` y `Turno` existen en el código como apoyo, pero se
omiten del diagrama para mantenerlo legible. En el diagrama sus valores se muestran como
atributos de tipo `String`.

## 3. Objetos de ejemplo

| Objeto | Clase | Valores |
|---|---|---|
| `ana` | `Dueno` | nombre = "Ana", apellido = "Ramirez", telefono = "9991234567", correo = "ana.ramirez@correo.com", direccion = "Calle 60 #123, Merida" |
| `rocky` | `Perro` | nombre = "Rocky", edad = 4, peso = 18.5, dueno = `ana`, raza = "Labrador", nivelEnergia = 4 (`hacerSonido()` devuelve "Guau") |
| `cita1` | `Cita` | mascota = `rocky`, veterinario = "Dr. Luis Lopez", consultorio = nº 1, fechaHora = 2026-11-03 10:00, servicio = `ConsultaGeneral` (motivo = "Revision anual"), estado = PROGRAMADA |

## 4. Clases descartadas

| Candidata | Por qué se descartó |
|---|---|
| `Sonido` | Es solo el texto que devuelve `hacerSonido()`; no tiene estado ni comportamiento propio. |
| `Vacuna` | Solo hacen falta su nombre y su costo, que ya son atributos de `Vacunacion`. |
| `RegistroConsulta` | Basta guardar cada consulta como texto en el historial; una clase aparte no aporta comportamiento. |

![Diagrama_Clasess.png](Diagrama_Clasess.png)