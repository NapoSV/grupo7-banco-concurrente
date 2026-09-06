# Multibanco Concurrente — Grupo 7

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://adoptium.net/)

**Universidad Evangélica de El Salvador** · Facultad de Ingeniería · Ingeniería en Desarrollo de Software y Ciencia de Datos
**Materia:** Programación II · **Semanas:** 7 y 8 · **Facilitador:** Ing. Daniel Enrique Guevara Gómez

---

## Descripción del proyecto

Plataforma **Multibanco Concurrente** que evoluciona el ejercicio del cajero automático hacia un ecosistema con **3 bancos ficticios** y **~20 clientes**, donde múltiples hilos ejecutan simultáneamente:

- Retiros locales (`LocalWithdraw`)
- Depósitos locales (`LocalDeposit`)
- Transferencias intramuros entre clientes del mismo banco (`LocalTransfer`)
- Transferencias interbancarias con **rollback automático** simulado (`InterBankTransfer`)

Se lanzan **100 transacciones concurrentes** usando `Callable<Receipt>` + `ExecutorService` + `Future`, con exclusión mutua vía `synchronized` en las cuentas y persistencia binaria (`.dat`) por cada comprobante.

Incluye una **interfaz gráfica Swing** (`MovementsViewer`) para consultar movimientos por cliente y por banco desde los archivos `.dat`.

---

## Arquitectura

```
com.sv.grupo7.banco
├── abstraction/       # Transaction (Callable<Receipt>)
├── controller/        # BankSimulation, MovementsViewer
├── dao/               # DaoTransaction (persistencia .dat + historial)
├── entities/          # Bank, BankAccount, Client, Receipt
├── implementation/    # LocalWithdraw, LocalDeposit, LocalTransfer, InterBankTransfer
├── interfaces/        # IDao<T>
└── shared/            # utilidades compartidas
```

---

## Requisitos

- **Java 21+** (Temurin / OpenJDK)
- **Maven 3.8+**
- IDE recomendado: **IntelliJ IDEA Community** o **VS Code + Extension Pack for Java**

---

## Compilar y ejecutar

```bash
# Paso 1 — Compilar el proyecto
mvn compile

# Paso 2 — Ejecutar la simulación (100 transacciones concurrentes)
#           Genera los archivos .dat en transacciones_finalizadas/
java -cp target/classes com.sv.grupo7.banco.controller.BankSimulation

# Paso 3 — Abrir el visor gráfico Swing
#           Requiere que ya existan los .dat del paso 2
java -cp target/classes com.sv.grupo7.banco.controller.MovementsViewer

# Opcional — Ejecutar los tests unitarios
mvn test
```

Los comprobantes se guardan como `transacciones_finalizadas/receta_<UUID>.dat`.

---

## Equipo del Grupo 7

| # | Nombre completo | CIF | Usuario GitHub | Tarea |
|---|---|---|---|---|
| 1 | LOPEZ RUIZ HECTOR NAPOLEON | 2026010132 | [@NapoSV](https://github.com/NapoSV) | `BankSimulation` + integración |
| 2 | VIGIL RAMIREZ ALEJANDRO ANTONIO | 2026010204 | [@Alejandro08vigil-dotcom](https://github.com/Alejandro08vigil-dotcom) | `Bank` |
| 3 | ORELLANA ROJAS BAYRON ALEXANDER | 2026011707 | [@crislomsu](https://github.com/crislomsu) | `Transaction` (abstracta) |
| 4 | DIAZ SANTOS ZAIR BENETT | 2026010796 | [@Djmaanco](https://github.com/Djmaanco) | `LocalWithdraw` |
| 5 | FLORES HERNANDEZ WALTER ALEJANDRO | 2026011012 | [@floreswa90](https://github.com/floreswa90) | Diagrama UML |
| 6 | MELGAR RIVAS WILLIAM ARIEL | 2026011736 | [@wamr01](https://github.com/wamr01) | `LocalDeposit` |
| 7 | MERINO VENTURA ALEJANDRO SEBASTIAN | 2026020122 | [@mknz05](https://github.com/mknz05) | `BankAccount` |
| 8 | FUENTES ORTIZ ERIKA ALEXANDRA | 2026011709 | [@fuenteserika221-spec](https://github.com/fuenteserika221-spec) | Pruebas unitarias JUnit 5 |
| 9 | VASQUEZ AMAYA WALTER AMILCAR | 2026010068 | [@WaltArt94](https://github.com/WaltArt94) | `DaoTransaction` + `MovementsViewer` (Swing) |
| 10 | VENTURA VELASQUEZ CARLOS MARIO | 2026011585 | [@mdealerdude](https://github.com/mdealerdude) | `InterBankTransfer` + `LocalTransfer` |
| 11 | SANCHEZ MENJIVAR NICOLE NOHEMY | 2026010813 | [@nicolenohemysanchez](https://github.com/nicolenohemysanchez) | `Receipt` + `Client` |

---

## Flujo Git para el equipo

```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-NN-<apellido>
# ... implementar tu clase ...
git add src/main/java/com/sv/grupo7/banco/<paquete>/<Archivo>.java
git commit -m "feat(tareaNN): <descripción corta>"
git push -u origin feature/tarea-NN-<apellido>
```

**Reglas:**
- Nadie hace merge — solo `push` a la rama personal. Las PRs las abre Héctor.
- Package obligatorio: `com.sv.grupo7.banco.*`
- Sin secretos ni datos personales en el código.

---

## Licencia

MIT — ver [`LICENSE`](LICENSE).
