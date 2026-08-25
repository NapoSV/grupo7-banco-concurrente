# Multibanco Concurrente — Grupo 7

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://adoptium.net/)

**Universidad Evangélica de El Salvador** · Facultad de Ingeniería · Ingeniería en Desarrollo de Software y Ciencia de Datos
**Materia:** Programación II · **Semana:** 7 · **Facilitador:** Ing. Daniel Enrique Guevara Gómez

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
- **Git 2.30+** con Git Bash (Windows)
- IDE recomendado: **IntelliJ IDEA Community** o **VS Code + Extension Pack for Java**

---

## Compilar y ejecutar

```bash
# Compilar todo el código
javac -d out $(find src/main/java -name "*.java")

# Ejecutar la simulación de 100 hilos concurrentes
java -cp out com.sv.grupo7.banco.controller.BankSimulation

# Abrir el visor Swing de movimientos
java -cp out com.sv.grupo7.banco.controller.MovementsViewer
```

Los comprobantes se guardan como `transacciones_finalizadas/receta_<UUID>.dat`.

---

## Equipo del Grupo 7

Ver tabla completa de asignación de tareas en [`docs/asignacion_tareas_grupo7.csv`](docs/asignacion_tareas_grupo7.csv) y la guía paso a paso para cada integrante en [`docs/GUIA_INTEGRANTES.md`](docs/GUIA_INTEGRANTES.md).

| # | Integrante | Tarea |
|---|---|---|
| 1 | LOPEZ RUIZ HECTOR NAPOLEON | `BankSimulation` + integración |
| 2 | VIGIL RAMIREZ ALEJANDRO ANTONIO | `Bank` |
| 3 | ORELLANA ROJAS BAYRON ALEXANDER | `Transaction` (abstracta) |
| 4 | DIAZ SANTOS ZAIR BENETT | `LocalWithdraw` |
| 5 | FLORES HERNANDEZ WALTER ALEJANDRO | Diagrama UML |
| 6 | MELGAR RIVAS WILLIAM ARIEL | `LocalDeposit` |
| 7 | MERINO VENTURA ALEJANDRO SEBASTIAN | `BankAccount` |
| 8 | FUENTES ORTIZ ERIKA ALEXANDRA | Pruebas unitarias JUnit 5 |
| 9 | VASQUEZ AMAYA WALTER AMILCAR | `DaoTransaction` + `MovementsViewer` (Swing) |
| 10 | VENTURA VELASQUEZ CARLOS MARIO | `InterBankTransfer` + `LocalTransfer` |
| 11 | SANCHEZ MENJIVAR NICOLE NOHEMY | `Receipt` + `Client` |

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
