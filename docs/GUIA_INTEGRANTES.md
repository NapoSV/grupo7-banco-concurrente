# Guía de Integrantes — Semana 7 · Multibanco Concurrente (Grupo 7)

**Universidad Evangélica de El Salvador** · Facultad de Ingeniería · **Programación II**
**Facilitador:** Ing. Daniel Enrique Guevara Gómez
**Deadline duro:** jueves 27/08/2026 a las 23:59

---

## Tabla de asignación rápida

| # | Integrante | Tarea | Ir a |
|---|---|---|---|
| 1 | LOPEZ RUIZ HECTOR NAPOLEON | `BankSimulation.java` + integración | [Tarea 1](#tarea-1--héctor-lópez) |
| 2 | VIGIL RAMIREZ ALEJANDRO ANTONIO | `Bank.java` | [Tarea 2](#tarea-2--alejandro-vigil) |
| 3 | ORELLANA ROJAS BAYRON ALEXANDER | `Transaction.java` (abstracta) | [Tarea 3](#tarea-3--bayron-orellana) |
| 4 | DIAZ SANTOS ZAIR BENETT | `LocalWithdraw.java` | [Tarea 4](#tarea-4--zair-díaz) |
| 5 | FLORES HERNANDEZ WALTER ALEJANDRO | `DIAGRAMA_GRUPO_7.pdf` (UML) | [Tarea 5](#tarea-5--walter-flores) |
| 6 | MELGAR RIVAS WILLIAM ARIEL | `LocalDeposit.java` | [Tarea 6](#tarea-6--william-melgar) |
| 7 | MERINO VENTURA ALEJANDRO SEBASTIAN | `BankAccount.java` | [Tarea 7](#tarea-7--alejandro-merino) |
| 8 | FUENTES ORTIZ ERIKA ALEXANDRA | Pruebas unitarias JUnit 5 | [Tarea 8](#tarea-8--erika-fuentes) |
| 9 | VASQUEZ AMAYA WALTER AMILCAR | `DaoTransaction` + `MovementsViewer` | [Tarea 9](#tarea-9--walter-vásquez) |
| 10 | VENTURA VELASQUEZ CARLOS MARIO | `InterBankTransfer` + `LocalTransfer` | [Tarea 10](#tarea-10--carlos-ventura) |
| 11 | SANCHEZ MENJIVAR NICOLE NOHEMY | `Receipt.java` + `Client.java` | [Tarea 11](#tarea-11--nicole-sánchez) |

---

## Reglas del equipo (léelas antes de empezar)

- **Java 21+ obligatorio.** Verifica con `java -version` antes de abrir el IDE.
- **Package raíz obligatorio:** `com.sv.grupo7.banco` — no lo cambies ni lo abrevies.
- **Nunca hagas merge** a `develop` ni a `main`. Solo haz push a tu rama `feature/tarea-NN-apellido`. Las PRs las abre **Héctor**.
- **Respeta las firmas públicas** (constructor y métodos `public`) del código de referencia — el resto del equipo depende de ellas para integrar.
- No incluyas contraseñas, IPs, datos personales ni información sensible en el código.
- Antes de hacer commit: asegúrate de que tu archivo no tiene errores de sintaxis.

---

## PASOS GENERALES (sigue este orden — aplica a todos)

### Paso 1 — Verifica que tienes Java 21+

Abre Git Bash, PowerShell o la terminal de tu IDE y corre:

```bash
java -version
```

Debe mostrar algo como `openjdk version "21"` o `java version "21"`. Si el número es menor a 21, descarga e instala JDK 21 desde:
**https://adoptium.net/** → selecciona Java 21 · JDK · tu sistema operativo (Windows x64 si usas Windows de 64 bits).

---

### Paso 2 — Verifica que tienes Git instalado

```bash
git --version
```

Debe mostrar `git version 2.x.x` o superior. Si no tienes Git: **https://git-scm.com/download/**

---

### Paso 3 — Acepta la invitación al repositorio

Revisa tu correo (el que registraste en GitHub) o ve directamente a:
**https://github.com/NapoSV/grupo7-banco-concurrente/invitations**

Acepta la invitación. Sin esto no podrás hacer `push` a tu rama.

---

### Paso 4 — Clona el repositorio

Abre Git Bash en la carpeta donde quieras trabajar (por ejemplo, tu escritorio o `Documentos/`) y corre:

```bash
git clone https://github.com/NapoSV/grupo7-banco-concurrente.git TareaS7_Banco
```

Esto crea una carpeta llamada `TareaS7_Banco` con todo el repositorio adentro. Espera a que termine (debe decir algo como `Resolving deltas: 100%`).

---

### Paso 5 — Entra a la carpeta del proyecto

```bash
cd TareaS7_Banco
```

---

### Paso 6 — Cambia a la rama `develop` y descarga lo último

```bash
git checkout develop
git pull origin develop
```

> **¿Por qué `develop` y no `main`?** La rama `develop` es donde está la estructura de carpetas actualizada. Al hacer `git checkout develop` verás la carpeta `src/` con todos los subpaquetes listos para que trabajes.

Después de esto deberías ver esta estructura de carpetas en tu proyecto:

```
TareaS7_Banco/
├── docs/
│   ├── GUIA_INTEGRANTES.md          ← esta guía
│   └── asignacion_tareas_grupo7.csv
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/sv/grupo7/banco/
│   │           ├── abstraction/     ← Tarea 3 (Bayron)
│   │           ├── controller/      ← Tarea 1 (Héctor) + Tarea 9 (Walter V.)
│   │           ├── dao/             ← Tarea 9 (Walter V.)
│   │           ├── entities/        ← Tareas 2, 7 y 11
│   │           ├── implementation/  ← Tareas 4, 6 y 10
│   │           ├── interfaces/      ← Tarea 9 (Walter V.)
│   │           └── shared/
│   └── test/
│       └── java/
│           └── com/sv/grupo7/banco/ ← Tareas 8 y 9
├── transacciones_finalizadas/       ← se llena cuando corre la simulación
├── .gitignore
├── LICENSE
└── README.md
```

> **Si no ves la carpeta `src/`:** corre `git pull origin develop` de nuevo y verifica que el prompt diga `develop` (no `main`).

---

### Paso 7 — Abre el proyecto en tu IDE

**Opción A — IntelliJ IDEA Community (recomendado):**
1. Abre IntelliJ IDEA.
2. Menú `File → Open`.
3. Navega y selecciona la carpeta `TareaS7_Banco` (la carpeta completa, no un archivo).
4. Clic en `OK`.
5. Si pregunta "Trust and Open Project?", clic en `Trust Project`.
6. En el panel izquierdo (Project), expande `src → main → java → com → sv → grupo7 → banco` para ver los subpaquetes.

**Opción B — VS Code con Extension Pack for Java:**
1. Menú `File → Open Folder`.
2. Selecciona la carpeta `TareaS7_Banco`.
3. Si VS Code pregunta si confías en la carpeta → clic en `Yes, I trust the authors`.
4. En el panel Explorer (izquierda), expande `src/main/java/com/sv/grupo7/banco/` para ver los subpaquetes.

---

### Paso 8 — Crea tu rama personal de trabajo

Sustituye `NN` por tu número de tarea y `apellido` por tu primer apellido en minúsculas:

```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-NN-apellido
```

**Ejemplos exactos según tarea:**
| Tarea | Comando exacto |
|---|---|
| 2 — Alejandro Vigil | `git checkout -b feature/tarea-02-vigil` |
| 3 — Bayron Orellana | `git checkout -b feature/tarea-03-orellana` |
| 4 — Zair Díaz | `git checkout -b feature/tarea-04-diaz` |
| 5 — Walter Flores | `git checkout -b feature/tarea-05-flores` |
| 6 — William Melgar | `git checkout -b feature/tarea-06-melgar` |
| 7 — Alejandro Merino | `git checkout -b feature/tarea-07-merino` |
| 8 — Erika Fuentes | `git checkout -b feature/tarea-08-fuentes` |
| 9 — Walter Vásquez | `git checkout -b feature/tarea-09-vasquez` |
| 10 — Carlos Ventura | `git checkout -b feature/tarea-10-ventura` |
| 11 — Nicole Sánchez | `git checkout -b feature/tarea-11-sanchez` |

---

### Paso 9 — Ve a tu sección y sigue los pasos

Haz clic en tu nombre en la tabla de asignación al principio del documento. Cada sección tiene pasos numerados específicos para tu tarea.

---

### Paso 10 — Avisa a Héctor cuando termines

Cuando ya hayas hecho `push`, manda un mensaje en el chat del Grupo 7 en Teams:

> "Héctor, listo la Tarea NN (`NombreDeClase.java`), ya hice push a `feature/tarea-NN-apellido`"

Héctor abrirá la Pull Request. **No hagas merge tú mismo.**

---

---

## Tarea 1 — Héctor López
**CIF:** 2026010132 · **Archivo:** `src/main/java/com/sv/grupo7/banco/controller/BankSimulation.java`

### 🎯 Lo que te toca
Escribir el **orquestador maestro** de la simulación: crear 3 bancos, ~20 clientes, distribuir cuentas entre ellos, generar un array de **100 transacciones aleatorias** de 4 tipos distintos, enviarlas a los `ExecutorService` de cada banco, recolectar los `Future<Receipt>`, persistirlos y mostrar la auditoría final. Además, te encargas de la **consolidación final** (mergear PRs, correr todo, empaquetar ZIP, exportar PDF).

### 🧠 Por qué es importante (aprendizaje)
Estás implementando un patrón **Producer-Consumer** con `ExecutorService` — el mismo que usan colas de mensajes (RabbitMQ, Kafka consumers), pools de workers HTTP, y sistemas de procesamiento batch. Aprendes a **coordinar múltiples pools de hilos independientes**, a **recolectar resultados asíncronos** con `Future.get()` y a hacer un **shutdown ordenado** para no perder trabajo en vuelo.

### 💼 Aplicación profesional real
- **Bancos reales**: cada motor transaccional (Swift, ACH, tarjetas) tiene su propio pool de hilos por región/canal. La orquestación luce igual.
- **APIs de alto tráfico**: Netflix, Uber, Spotify usan patrones idénticos con `CompletableFuture` para paralelizar llamadas a microservicios.
- **ETL de datos**: Airflow, Spark y Dagster corren tareas en paralelo con la misma estructura conceptual.

### 🔁 Cómo lo puedes reaprovechar
En cualquier proyecto donde necesites paralelizar N tareas independientes: procesamiento de imágenes, envío masivo de correos, scraping web, backtests financieros.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-01-lopez
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** en el panel Project, navega hasta `src/main/java/com/sv/grupo7/banco/controller/` → clic derecho → `New → Java Class` → escribe `BankSimulation` → Enter.
- **VS Code:** en el Explorer, navega hasta `src/main/java/com/sv/grupo7/banco/controller/` → clic en el ícono `New File` → escribe `BankSimulation.java` → Enter.

**Paso 3 — Pegar el código**
Borra todo el contenido autogenerado y pega el código de referencia de abajo exactamente como está.

**Paso 4 — Verificar que no hay errores de sintaxis**
- **IntelliJ:** `Ctrl+F9` (Build). Ignora errores de clases ausentes (Receipt, Bank, etc.) — son de tus compañeros. Solo importa que no haya subrayados rojos en TU archivo por errores de sintaxis.
- **VS Code:** el panel de Problemas (`Ctrl+Shift+M`) no debe mostrar errores en `BankSimulation.java`.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/controller/BankSimulation.java
git commit -m "feat(tarea01): orquestador BankSimulation con 100 hilos concurrentes"
git push -u origin feature/tarea-01-lopez
```

**Paso 6 — Avisar a Héctor** (aplica también si eres Héctor — cierra la tarea en el planner).

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.controller;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.*;
import com.sv.grupo7.banco.implementation.*;

import java.util.*;
import java.util.concurrent.Future;

public class BankSimulation {

    private static final int TOTAL_TRANSACCIONES = 100;

    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN MULTIBANCO CONCURRENTE — GRUPO 7 ===\n");

        // 1. Sembrar bancos (cada uno con su ExecutorService de 10 hilos)
        List<Bank> banks = List.of(
                new Bank("Banco Cuscatlán G7", 10),
                new Bank("Banco Agrícola G7", 10),
                new Bank("Banco Davivienda G7", 10)
        );

        // 2. Sembrar 20 clientes ficticios
        List<Client> clients = seedClients();

        // 3. Distribuir 1-2 cuentas por cliente entre los 3 bancos
        seedAccounts(banks, clients);

        // 4. Generar 100 transacciones aleatorias
        Random rnd = new Random(42);
        Transaction[] transactions = new Transaction[TOTAL_TRANSACCIONES];
        for (int i = 0; i < TOTAL_TRANSACCIONES; i++) {
            transactions[i] = buildRandomTransaction(banks, rnd);
        }

        // 5. Enviar cada transacción al pool del banco de origen
        List<Future<Receipt>> futures = new ArrayList<>();
        for (Transaction tx : transactions) {
            futures.add(tx.getOriginBank().submit(tx));
        }

        // 6. Recoger resultados y persistir en .dat
        DaoTransaction dao = new DaoTransaction();
        int ok = 0, failed = 0, rolled = 0;
        for (Future<Receipt> f : futures) {
            try {
                Receipt r = f.get();
                dao.insert(r);
                switch (r.status()) {
                    case "SUCCESS"     -> ok++;
                    case "FAILED"      -> failed++;
                    case "ROLLED_BACK" -> rolled++;
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Future falló: " + e.getMessage());
            }
        }

        // 7. Apagar pools y reportar auditoría
        banks.forEach(Bank::shutdown);
        System.out.printf("%n=== RESUMEN: %d exitosas | %d fallidas | %d revertidas ===%n",
                ok, failed, rolled);
        banks.forEach(Bank::printAuditReport);

        // 8. Historial por cliente (los primeros 3 para no saturar consola)
        System.out.println("\n=== HISTORIAL POR CLIENTE (muestra) ===");
        clients.stream().limit(3).forEach(c -> dao.printHistoryFor(c.getId()));
    }

    private static List<Client> seedClients() {
        return List.of(
                new Client("C01", "Juan",     "Pérez"),
                new Client("C02", "María",    "López"),
                new Client("C03", "Carlos",   "Martínez"),
                new Client("C04", "Ana",      "Ramírez"),
                new Client("C05", "Luis",     "Hernández"),
                new Client("C06", "Sofía",    "González"),
                new Client("C07", "Diego",    "Torres"),
                new Client("C08", "Valeria",  "Cruz"),
                new Client("C09", "Andrés",   "Flores"),
                new Client("C10", "Camila",   "Vásquez"),
                new Client("C11", "Ricardo",  "Mejía"),
                new Client("C12", "Isabel",   "Aguilar"),
                new Client("C13", "Fernando", "Rivera"),
                new Client("C14", "Gabriela", "Chávez"),
                new Client("C15", "Roberto",  "Molina"),
                new Client("C16", "Patricia", "Castillo"),
                new Client("C17", "Miguel",   "Reyes"),
                new Client("C18", "Adriana",  "Guzmán"),
                new Client("C19", "José",     "Alvarado"),
                new Client("C20", "Karla",    "Beltrán")
        );
    }

    private static void seedAccounts(List<Bank> banks, List<Client> clients) {
        int accCounter = 1;
        Random rnd = new Random(7);
        for (Client c : clients) {
            int numCuentas = 1 + rnd.nextInt(2);
            for (int i = 0; i < numCuentas; i++) {
                Bank b = banks.get(rnd.nextInt(banks.size()));
                String accNum = String.format("ACC-%04d", accCounter++);
                b.addAccount(new BankAccount(accNum, c.getId(), b.getName(), 1000.0));
            }
        }
    }

    private static Transaction buildRandomTransaction(List<Bank> banks, Random rnd) {
        int tipo = rnd.nextInt(4);
        double amount = 50 + rnd.nextInt(200);
        Bank b1 = banks.get(rnd.nextInt(banks.size()));
        BankAccount a1 = randomAccount(b1, rnd);
        return switch (tipo) {
            case 0 -> new LocalWithdraw(b1, a1, amount);
            case 1 -> new LocalDeposit(b1, a1, amount);
            case 2 -> {
                BankAccount a2 = randomAccount(b1, rnd);
                yield new LocalTransfer(b1, a1, a2, amount);
            }
            default -> {
                Bank b2 = banks.get((banks.indexOf(b1) + 1) % banks.size());
                BankAccount a2 = randomAccount(b2, rnd);
                yield new InterBankTransfer(b1, b2, a1, a2, amount);
            }
        };
    }

    private static BankAccount randomAccount(Bank b, Random rnd) {
        List<BankAccount> list = new ArrayList<>(b.getAllAccounts());
        return list.get(rnd.nextInt(list.size()));
    }
}
```

---

## Tarea 2 — Alejandro Vigil
**CIF:** 2026010204 · **Archivo:** `src/main/java/com/sv/grupo7/banco/entities/Bank.java`

### 🎯 Lo que te toca
Implementar la clase `Bank`: administra un `HashMap<String, BankAccount>` con las cuentas del banco y su **propio `ExecutorService`** para atender transacciones en paralelo. Expone `submit()` para encolar `Transaction`, `shutdown()` para cerrar limpio, y `printAuditReport()` para imprimir el saldo final auditado.

### 🧠 Por qué es importante
Aprendes a **encapsular un thread pool** dentro de una entidad de dominio — patrón fundamental de los actores/servicios en sistemas distribuidos. Cada `Bank` es autónomo: gestiona sus recursos y no comparte hilos con otros bancos.

### 💼 Aplicación profesional real
- **Microservicios**: cada servicio tiene su propio pool de hilos para no bloquear a los demás (aislamiento por bulkhead pattern).
- **Bases de datos**: HikariCP, PgBouncer aplican el mismo concepto pero con conexiones en vez de hilos.
- **Actor systems** (Akka, Elixir): cada actor tiene su propio mailbox y scheduler.

### 🔁 Cómo lo puedes reaprovechar
Cada vez que necesites un componente autónomo con su propio worker pool: servicios de notificaciones, procesadores de colas, backends de renderizado.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-02-vigil
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** panel Project → navega hasta `src/main/java/com/sv/grupo7/banco/entities/` → clic derecho → `New → Java Class` → escribe `Bank` → Enter.
- **VS Code:** Explorer → navega hasta `src/main/java/com/sv/grupo7/banco/entities/` → ícono `New File` → escribe `Bank.java` → Enter.

**Paso 3 — Pegar el código**
Borra todo lo autogenerado y pega el código de referencia de abajo.

**Paso 4 — Verificar sintaxis**
Compila solo tu archivo. Errores como `cannot find symbol Receipt` son normales (Receipt la hace Nicole). Solo deben quedar en verde los errores propios de sintaxis.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/entities/Bank.java
git commit -m "feat(tarea02): entidad Bank con ExecutorService propio"
git push -u origin feature/tarea-02-vigil
```

**Paso 6 — Avisar a Héctor por Teams:**
> "Héctor, listo Tarea 2 (Bank.java), push a feature/tarea-02-vigil"

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.entities;

import com.sv.grupo7.banco.abstraction.Transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Bank {

    private final String name;
    private final HashMap<String, BankAccount> accounts = new HashMap<>();
    private final ExecutorService executor;

    public Bank(String name, int poolSize) {
        this.name = name;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public String getName() {
        return name;
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Collection<BankAccount> getAllAccounts() {
        return accounts.values();
    }

    public Future<Receipt> submit(Transaction transaction) {
        return executor.submit(transaction);
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void printAuditReport() {
        System.out.printf("%n--- AUDITORÍA: %s ---%n", name.toUpperCase());
        double total = 0;
        for (BankAccount acc : accounts.values()) {
            System.out.printf("  %s (cliente %s) => $%.2f%n",
                    acc.getAccountNumber(), acc.getClientId(), acc.getBalance());
            total += acc.getBalance();
        }
        System.out.printf("  TOTAL BANCO: $%.2f%n", total);
    }
}
```

---

## Tarea 3 — Bayron Orellana
**CIF:** 2026011707 · **Archivo:** `src/main/java/com/sv/grupo7/banco/abstraction/Transaction.java`

### 🎯 Lo que te toca
Definir la **clase abstracta `Transaction`** que implementa `Callable<Receipt>`. Todas las transacciones concretas (retiro, depósito, transferencia local, interbancaria) heredan de aquí. Debe generar automáticamente un `transactionId` único, exponer `getOriginBank()` (para que el orquestador sepa a qué banco enviar) y forzar a las subclases a implementar `call()`.

### 🧠 Por qué es importante
Este es el corazón del **polimorfismo + concurrencia**: gracias a que `Transaction` implementa `Callable<Receipt>`, el `ExecutorService` puede ejecutar cualquier subclase sin conocerla — es el patrón **Command** combinado con **Template Method**. Además, `Callable` (a diferencia de `Runnable`) permite **retornar un valor** y **propagar excepciones** — indispensable para el rollback.

### 💼 Aplicación profesional real
- **Colas de trabajos** (Sidekiq, Celery, Bull): cada job es una clase que hereda de una base y se ejecuta en workers.
- **Handlers de eventos** en arquitecturas CQRS/Event Sourcing.
- **Comandos CLI** en frameworks como Spring Shell o Micronaut.

### 🔁 Cómo lo puedes reaprovechar
Cualquier flujo donde tengas "operaciones intercambiables" (envío de emails, generación de PDFs, procesamiento de imágenes) puede modelarse con esta jerarquía.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-03-orellana
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** panel Project → `src/main/java/com/sv/grupo7/banco/abstraction/` → clic derecho → `New → Java Class` → escribe `Transaction` → en el menú que aparece elige `Abstract Class` (o escribe la clase como `abstract class` manualmente).
- **VS Code:** Explorer → `src/main/java/com/sv/grupo7/banco/abstraction/` → ícono `New File` → `Transaction.java`.

**Paso 3 — Pegar el código**
Borra todo lo autogenerado y pega el código de referencia.

**Paso 4 — Verificar sintaxis**
`Callable<Receipt>` dará error si Receipt no está aún — es normal. Verifica que no haya errores de sintaxis en tu propio archivo.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/abstraction/Transaction.java
git commit -m "feat(tarea03): clase abstracta Transaction (Callable<Receipt>)"
git push -u origin feature/tarea-03-orellana
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 3 (Transaction.java), push a feature/tarea-03-orellana"

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.abstraction;

import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.Receipt;

import java.util.UUID;
import java.util.concurrent.Callable;

public abstract class Transaction implements Callable<Receipt> {

    protected final String transactionId;
    protected final String type;
    protected final double amount;

    protected Transaction(String type, double amount) {
        this.transactionId = UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.amount = amount;
    }

    /** Cada transacción indica en qué banco se origina, para que el orquestador la envíe al pool correcto. */
    public abstract Bank getOriginBank();

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
```

---

## Tarea 4 — Zair Díaz
**CIF:** 2026010796 · **Archivo:** `src/main/java/com/sv/grupo7/banco/implementation/LocalWithdraw.java`

### 🎯 Lo que te toca
Implementar `LocalWithdraw`: una `Transaction` que realiza un **retiro local sincronizado** sobre una `BankAccount`. Si hay fondos → `SUCCESS`. Si no → `FAILED`. Devuelve un `Receipt` con el resultado.

### 🧠 Por qué es importante
Es tu primer contacto con **exclusión mutua**: aunque no llamas directamente a `synchronized`, tu código se ejecuta desde múltiples hilos simultáneos que golpean la misma cuenta. Confiamos en que `BankAccount.withdraw()` es sincronizado — pero tú debes registrar correctamente el estado.

### 💼 Aplicación profesional real
- **ATMs reales**: cada retiro es exactamente esta operación, con logs firmados.
- **E-commerce**: descuento de inventario cuando alguien compra un producto.
- **Videojuegos multiplayer**: consumo de recursos (oro, munición) sincronizado por jugador.

### 🔁 Cómo lo puedes reaprovechar
Cualquier operación de **débito atómico**: cupones, saldos de gift cards, créditos prepagados.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-04-diaz
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/implementation/` → clic derecho → `New → Java Class` → `LocalWithdraw`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/implementation/` → `New File` → `LocalWithdraw.java`.

**Paso 3 — Pegar el código** de la sección de abajo.

**Paso 4 — Verificar sintaxis.** Errores de clases ausentes (Transaction, BankAccount, Receipt) son normales.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/implementation/LocalWithdraw.java
git commit -m "feat(tarea04): implementar LocalWithdraw (retiro sincronizado)"
git push -u origin feature/tarea-04-diaz
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 4 (LocalWithdraw.java), push a feature/tarea-04-diaz"

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.implementation;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.BankAccount;
import com.sv.grupo7.banco.entities.Receipt;

import java.time.LocalDateTime;

public class LocalWithdraw extends Transaction {

    private final Bank bank;
    private final BankAccount account;

    public LocalWithdraw(Bank bank, BankAccount account, double amount) {
        super("WITHDRAW_LOCAL", amount);
        this.bank = bank;
        this.account = account;
    }

    @Override
    public Bank getOriginBank() {
        return bank;
    }

    @Override
    public Receipt call() {
        System.out.printf("[%s] %s → Retiro local $%.2f de %s (cliente %s)%n",
                Thread.currentThread().getName(), transactionId,
                amount, account.getAccountNumber(), account.getClientId());

        boolean ok = account.withdraw(amount);
        String status = ok ? "SUCCESS" : "FAILED";

        System.out.printf("[%s] %s → %s%n",
                Thread.currentThread().getName(), transactionId, status);

        return new Receipt(
                transactionId, type,
                account.getClientId(), null,
                account.getAccountNumber(), null,
                bank.getName(), null,
                amount, LocalDateTime.now(), status
        );
    }
}
```

---

## Tarea 5 — Walter Flores
**CIF:** 2026011012 · **Archivo:** `docs/DIAGRAMA_GRUPO_7.pdf` (+ opcional `docs/DIAGRAMA_GRUPO_7.drawio` o `.puml`)

### 🎯 Lo que te toca
Crear el **Diagrama de Clases UML** que muestra las relaciones entre `Bank`, `BankAccount`, `Client`, `Receipt`, `ExecutorService`, `Transaction` (abstracta) y sus 4 subclases concretas (`LocalWithdraw`, `LocalDeposit`, `LocalTransfer`, `InterBankTransfer`). Exportarlo como **PDF** con el nombre `DIAGRAMA_GRUPO_7.pdf`.

### 🧠 Por qué es importante
El UML es el **lenguaje visual** con que los desarrolladores comunican arquitectura. Aprender a leer y crear diagramas de clases es imprescindible para trabajar en equipo, hacer code reviews y documentar sistemas.

### 💼 Aplicación profesional real
- **Diseño de APIs y microservicios**: casi todo squad usa Miro, Whimsical, Lucidchart o PlantUML.
- **Onboarding**: los diagramas son lo primero que muestran a un dev nuevo.
- **Auditorías técnicas**: consultoras (Deloitte, EY) exigen diagramas UML actualizados.

### 🔁 Cómo lo puedes reaprovechar
En cualquier proyecto donde documentes modelo de dominio, DDD, o hagas propuestas técnicas para clientes.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-05-flores
```

**Paso 2 — Abrir draw.io**
Ve a **https://app.diagrams.net/** en tu navegador. No necesitas cuenta. Elige `Create New Diagram → Blank Diagram`.

**Paso 3 — Crear el diagrama**
Dibuja cada clase según la especificación completa de abajo. Para agregar una clase en draw.io:
- Doble clic en el lienzo → aparece el menú de formas.
- Busca "UML Class" en el buscador o usa la categoría UML del panel izquierdo.
- Arrastra la forma al lienzo y edita el nombre/atributos/métodos.

**Paso 4 — Guardar el archivo fuente**
`File → Save As → Device` → guarda como `DIAGRAMA_GRUPO_7.drawio` en tu carpeta local.

**Paso 5 — Exportar a PDF**
`File → Export As → PDF` → guarda como `DIAGRAMA_GRUPO_7.pdf`.

**Paso 6 — Copiar los archivos al repo**
Copia `DIAGRAMA_GRUPO_7.pdf` (y si quieres `DIAGRAMA_GRUPO_7.drawio`) a la carpeta `docs/` dentro de `TareaS7_Banco/`.

**Paso 7 — Commit y push**
```bash
git add docs/DIAGRAMA_GRUPO_7.pdf
git add docs/DIAGRAMA_GRUPO_7.drawio
git commit -m "docs(tarea05): agregar diagrama UML del sistema Multibanco"
git push -u origin feature/tarea-05-flores
```

**Paso 8 — Avisar a Héctor:**
> "Héctor, listo Tarea 5 (DIAGRAMA_GRUPO_7.pdf), push a feature/tarea-05-flores"

### 📝 Herramientas sugeridas
- **draw.io** (gratis, web): https://app.diagrams.net/ → `File → Export as → PDF`
- **PlantUML** (texto → diagrama): https://www.plantuml.com/plantuml/
- **Mermaid** (dentro de VS Code con extensión Markdown Preview Mermaid): más rápido para versionar en Git

### 📐 Especificación completa del diagrama UML

A continuación tienes **todas las clases** que deben aparecer en el diagrama, con sus **atributos, métodos, visibilidad, tipos y relaciones**. Junto a cada bloque encuentras el **porqué** — para que entiendas qué modela cada parte del sistema mientras la dibujas.

> **Convenciones UML que debes usar en tu diagrama:**
> - Visibilidad: `+` public, `-` private, `#` protected, `~` package
> - Atributos: `visibilidad nombre : Tipo`
> - Métodos: `visibilidad nombre(param : Tipo) : TipoRetorno`
> - Clase abstracta → nombre en *cursiva* o con `{abstract}`
> - Interfaz → estereotipo `«interface»` sobre el nombre
> - `record` → estereotipo `«record»`

---

#### 🔷 Clase 1 — `Client` (entities)

```
─────────────────────────────
        Client
─────────────────────────────
  - id       : String
  - name     : String
  - lastName : String
─────────────────────────────
  + Client(id, name, lastName)
  + getId()       : String
  + getName()     : String
  + getLastName() : String
  + getFullName() : String
─────────────────────────────
```
**Por qué así:** `Client` es una **entidad de dominio** — representa a la persona titular. Los atributos son `final` (inmutables) porque un cliente no cambia de identidad. Implementa `Serializable` (marca con estereotipo `«Serializable»` en el diagrama) para poder guardarse a `.dat` si hiciera falta.

---

#### 🔷 Clase 2 — `BankAccount` (entities)

```
─────────────────────────────
        BankAccount
─────────────────────────────
  - accountNumber : String
  - clientId      : String
  - bankName      : String
  - balance       : double
─────────────────────────────
  + BankAccount(accountNumber, clientId, bankName, initialBalance)
  + withdraw(amount : double) : boolean   {synchronized}
  + deposit(amount : double)  : void      {synchronized}
  + getBalance()              : double    {synchronized}
  + getAccountNumber()        : String
  + getClientId()             : String
  + getBankName()             : String
─────────────────────────────
```
**Por qué así:** Los métodos que tocan `balance` son `{synchronized}` — marca esa restricción explícitamente en el diagrama porque es **la razón académica de todo el ejercicio** (exclusión mutua para evitar race conditions). El campo `clientId` es un **identificador foráneo** al `Client` — no una referencia directa al objeto — para simular cómo se hace en bases de datos relacionales.

---

#### 🔷 Clase 3 — `Bank` (entities)

```
─────────────────────────────
          Bank
─────────────────────────────
  - name     : String
  - accounts : HashMap<String, BankAccount>
  - executor : ExecutorService
─────────────────────────────
  + Bank(name : String, poolSize : int)
  + getName()                             : String
  + addAccount(account : BankAccount)     : void
  + getAccount(accountNumber : String)    : BankAccount
  + getAllAccounts()                      : Collection<BankAccount>
  + submit(transaction : Transaction)     : Future<Receipt>
  + shutdown()                            : void
  + printAuditReport()                    : void
─────────────────────────────
```
**Por qué así:** `Bank` es un **Aggregate Root** (patrón DDD): agrupa cuentas y gestiona su propio pool de hilos. Ojo con `submit()` — retorna `Future<Receipt>`, esa firma es la conexión con `Transaction` (que es `Callable<Receipt>`). El `ExecutorService` es un atributo *interno* → la relación con él es **composición** (rombo negro relleno).

---

#### 🔷 Clase 4 — `Receipt` (entities) — `«record»`

```
─────────────────────────────
   «record»  Receipt
─────────────────────────────
  transactionId    : String
  type             : String
  clientIdOrigen   : String
  clientIdDestino  : String
  cuentaOrigen     : String
  cuentaDestino    : String
  bancoOrigen      : String
  bancoDestino     : String
  amount           : double
  timestamp        : LocalDateTime
  status           : String
─────────────────────────────
  + toString() : String
─────────────────────────────
```
**Por qué así:** Es un **objeto de valor inmutable** — un comprobante nunca cambia después de emitido. Marca la clase como `«record»` y como `«Serializable»`. El campo `status` toma solo 3 valores: `SUCCESS`, `FAILED`, `ROLLED_BACK`.

---

#### 🔷 Clase 5 — `Transaction` (abstraction) — **clase abstracta**

```
─────────────────────────────
   *Transaction*   {abstract}
─────────────────────────────
  # transactionId : String
  # type          : String
  # amount        : double
─────────────────────────────
  # Transaction(type : String, amount : double)
  + getOriginBank()   : Bank      {abstract}
  + call()            : Receipt   {abstract, throws Exception}
  + getTransactionId(): String
  + getType()         : String
  + getAmount()       : double
─────────────────────────────
```
**Por qué así:** Este es el **corazón polimórfico** del sistema. Marca la clase en *cursiva* o con `{abstract}`. Los métodos `getOriginBank()` y `call()` son abstractos → cada subclase debe implementarlos. La visibilidad de atributos es `protected` (`#`) porque los subtipos los usan directamente. **Debe aparecer una flecha de realización (línea discontinua con triángulo vacío) hacia la interfaz `Callable<Receipt>`** de `java.util.concurrent`.

---

#### 🔷 Clases 6–9 — Las 4 implementaciones concretas de `Transaction`

```
────────────────────────────         ────────────────────────────
     LocalWithdraw                        LocalDeposit
────────────────────────────         ────────────────────────────
  - bank    : Bank                    - bank    : Bank
  - account : BankAccount             - account : BankAccount
────────────────────────────         ────────────────────────────
  + LocalWithdraw(bank, account,      + LocalDeposit(bank, account,
                  amount)                             amount)
  + getOriginBank() : Bank            + getOriginBank() : Bank
  + call()          : Receipt         + call()          : Receipt
────────────────────────────         ────────────────────────────

────────────────────────────         ────────────────────────────
      LocalTransfer                       InterBankTransfer
────────────────────────────         ────────────────────────────
  - bank    : Bank                    - bancoOrigen    : Bank
  - origen  : BankAccount             - bancoDestino   : Bank
  - destino : BankAccount             - cuentaOrigen   : BankAccount
                                      - cuentaDestino  : BankAccount
                                      - random         : Random
────────────────────────────         ────────────────────────────
  + LocalTransfer(bank, origen,       + InterBankTransfer(bancoOrig,
                  destino, amount)          bancoDest, ctaOrig,
                                            ctaDest, amount)
  + getOriginBank() : Bank            + getOriginBank() : Bank
  + call()          : Receipt         + call()          : Receipt
                                      - buildReceipt(status) : Receipt
────────────────────────────         ────────────────────────────
```
**Por qué así:**
- `LocalWithdraw` y `LocalDeposit` operan sobre **una sola cuenta** → una asociación con `BankAccount` de multiplicidad `1`.
- `LocalTransfer` opera sobre **dos cuentas del mismo banco** → dos asociaciones con `BankAccount` (roles `origen` y `destino`) y **una** asociación con `Bank`.
- `InterBankTransfer` opera sobre **dos cuentas de bancos distintos** → dos asociaciones con `BankAccount` **y dos asociaciones con `Bank`** (roles `bancoOrigen` y `bancoDestino`). Además tiene el campo privado `random` que modela la probabilidad de éxito 85/15.

**Todas heredan de `Transaction`** → dibuja **4 flechas de herencia** (línea continua con triángulo vacío) apuntando a la clase abstracta.

---

#### 🔷 Clase 10 — `IDao<T>` (interfaces) — **interfaz genérica**

```
─────────────────────────────
   «interface»  IDao<T>
─────────────────────────────
  + insert(item : T)     : void
  + readAll()            : List<T>
─────────────────────────────
```
**Por qué así:** Interfaz **genérica** (`<T>`) → esto es una mejora sobre el ejemplo del ingeniero. Marca el estereotipo `«interface»`. Cualquier DAO futuro (`DaoClient`, `DaoAccount`) podría implementarla — reutilización a nivel de contrato.

---

#### 🔷 Clase 11 — `DaoTransaction` (dao)

```
─────────────────────────────
       DaoTransaction
─────────────────────────────
  - DIR : String  {static final = "transacciones_finalizadas"}
─────────────────────────────
  + DaoTransaction()
  + insert(receipt : Receipt)         : void      {synchronized}
  + readAll()                         : List<Receipt>
  + historyFor(clientId : String)     : List<Receipt>
  + printHistoryFor(clientId : String): void
─────────────────────────────
```
**Por qué así:** Implementa `IDao<Receipt>` → dibuja una **flecha de realización** (línea discontinua + triángulo vacío) hacia `IDao<Receipt>`. El método `insert()` es `synchronized` para evitar que dos hilos escriban el mismo archivo al mismo tiempo. `historyFor()` y `printHistoryFor()` son extensiones específicas de este DAO — no están en la interfaz porque son funcionalidad de valor añadido.

---

#### 🔷 Clase 12 — `BankSimulation` (controller)

```
─────────────────────────────
       BankSimulation
─────────────────────────────
  - TOTAL_TRANSACCIONES : int  {static final = 100}
─────────────────────────────
  + main(args : String[]) : void  {static}
  - seedClients()                          : List<Client>       {static}
  - seedAccounts(banks, clients)           : void               {static}
  - buildRandomTransaction(banks, rnd)     : Transaction        {static}
  - randomAccount(bank, rnd)               : BankAccount        {static}
─────────────────────────────
```
**Por qué así:** Es el **orquestador**. No tiene estado propio (todos los métodos son `static`) porque su único propósito es coordinar. En el diagrama, las relaciones que salen de `BankSimulation` son **dependencias** (línea discontinua con flecha), no asociaciones — porque no guarda referencias, solo las usa temporalmente en `main()`.

---

#### 🔷 Clase 13 — `MovementsViewer` (controller) — extiende `JFrame`

```
─────────────────────────────
      MovementsViewer
─────────────────────────────
  - dao   : DaoTransaction
  - model : DefaultTableModel
─────────────────────────────
  + MovementsViewer()
  + main(args : String[])  : void  {static}
─────────────────────────────
```
**Por qué así:** Es la **UI de auditoría**. Extiende `JFrame` de Swing → dibuja una flecha de herencia hacia `javax.swing.JFrame` (puedes representarla como una caja externa gris para clases del JDK). Depende de `DaoTransaction` (composición: la crea internamente) y presenta objetos `Receipt` en su tabla.

---

### 🔗 Relaciones que deben aparecer en el diagrama

Aquí está la lista **completa** de las relaciones a dibujar, con el tipo de flecha UML correcto y **por qué existe cada una**:

| # | Origen | Tipo de relación | Destino | Multiplicidad | Por qué existe |
|---|---|---|---|---|---|
| 1 | `Bank` | **Composición** (◆──) | `BankAccount` | `1` ── `0..*` | Un banco *contiene* sus cuentas; si el banco desaparece, las cuentas también (ciclo de vida ligado). |
| 2 | `Bank` | **Composición** (◆──) | `ExecutorService` | `1` ── `1` | Cada banco tiene su propio pool de hilos privado — nadie más lo usa. |
| 3 | `BankAccount` | **Asociación** (──▶) | `Client` (por `clientId`) | `*` ── `1` | La cuenta pertenece a un cliente; se referencia por ID (no por objeto) para simular una clave foránea de BD. |
| 4 | `Transaction` | **Realización** (─ ─▷) | `«interface» Callable<Receipt>` | — | Permite que el `ExecutorService` la ejecute y retorne un `Receipt` vía `Future.get()`. |
| 5 | `LocalWithdraw` | **Herencia** (──▷) | `Transaction` | — | Especialización del comportamiento base. |
| 6 | `LocalDeposit` | **Herencia** (──▷) | `Transaction` | — | Idem. |
| 7 | `LocalTransfer` | **Herencia** (──▷) | `Transaction` | — | Idem. |
| 8 | `InterBankTransfer` | **Herencia** (──▷) | `Transaction` | — | Idem. |
| 9 | `LocalWithdraw` | **Asociación** (──▶) | `BankAccount` | `1` ── `1` | Necesita la cuenta sobre la cual retirar. |
| 10 | `LocalWithdraw` | **Asociación** (──▶) | `Bank` | `1` ── `1` | Debe saber a qué banco reportar como origen. |
| 11 | `LocalDeposit` | **Asociación** (──▶) | `BankAccount` | `1` ── `1` | Idem, para depositar. |
| 12 | `LocalDeposit` | **Asociación** (──▶) | `Bank` | `1` ── `1` | Idem. |
| 13 | `LocalTransfer` | **Asociación** (──▶) | `BankAccount` | `2` (roles `origen`, `destino`) | Mueve dinero entre dos cuentas del mismo banco. |
| 14 | `LocalTransfer` | **Asociación** (──▶) | `Bank` | `1` ── `1` | Ambas cuentas están en el mismo banco. |
| 15 | `InterBankTransfer` | **Asociación** (──▶) | `BankAccount` | `2` (roles `cuentaOrigen`, `cuentaDestino`) | Mueve dinero entre dos cuentas de bancos distintos. |
| 16 | `InterBankTransfer` | **Asociación** (──▶) | `Bank` | `2` (roles `bancoOrigen`, `bancoDestino`) | Requiere ambos bancos para reportar y ejecutar rollback. |
| 17 | `DaoTransaction` | **Realización** (─ ─▷) | `«interface» IDao<Receipt>` | — | Cumple el contrato genérico del DAO. |
| 18 | `DaoTransaction` | **Dependencia** (─ ─▶) | `Receipt` | — | Persiste y lee comprobantes. |
| 19 | `Receipt` | **Realización** (─ ─▷) | `«interface» Serializable` | — | Necesario para escribir el objeto a `.dat`. |
| 20 | `Client` | **Realización** (─ ─▷) | `«interface» Serializable` | — | Idem. |
| 21 | `MovementsViewer` | **Herencia** (──▷) | `JFrame` (Swing) | — | Es la ventana de la UI. |
| 22 | `MovementsViewer` | **Composición** (◆──) | `DaoTransaction` | `1` ── `1` | Crea internamente el DAO para leer los `.dat`. |
| 23 | `BankSimulation` | **Dependencia** (─ ─▶) | `Bank`, `Client`, `BankAccount`, `Transaction`, `DaoTransaction` | — | Instancia y coordina, pero no las guarda como atributos. |

---

### 🎨 Sugerencia de layout (cómo organizar las cajas en la hoja)

- **Zona superior (entidades del dominio):** `Client`, `BankAccount`, `Bank`, `Receipt`. Estas son el "modelo" — colócalas alineadas horizontalmente.
- **Zona central (jerarquía polimórfica):** `Transaction` en el centro con las 4 subclases debajo desplegadas en abanico.
- **Zona inferior izquierda (persistencia):** `IDao<Receipt>` → `DaoTransaction`.
- **Zona inferior derecha (controllers):** `BankSimulation` y `MovementsViewer`.
- **Interfaces del JDK (`Callable<Receipt>`, `Serializable`, `JFrame`):** dibújalas como cajas más pequeñas con fondo gris, colocadas en los bordes del diagrama.
- **Multiplicidades siempre visibles** (`1`, `0..*`, `2`) al lado de cada extremo de línea.
- **Roles de asociación** (`origen`, `destino`, `bancoOrigen`, `bancoDestino`) al lado de la punta de flecha correspondiente.

---

### ✅ Checklist antes de exportar a PDF

- [ ] Aparecen las **13 clases/interfaces** listadas arriba.
- [ ] `Transaction` está en cursiva o marcada `{abstract}`.
- [ ] `IDao<T>` y `Callable`, `Serializable`, `JFrame` están marcadas `«interface»` o con caja diferenciada.
- [ ] `Receipt` está marcada `«record»`.
- [ ] Los métodos `synchronized` de `BankAccount` y el `insert()` de `DaoTransaction` están anotados.
- [ ] Las 4 subclases de `Transaction` tienen flecha de **herencia** (triángulo vacío + línea continua).
- [ ] `DaoTransaction → IDao<Receipt>` y `Transaction → Callable<Receipt>` usan flecha de **realización** (triángulo vacío + línea discontinua).
- [ ] `Bank ◆── BankAccount` y `Bank ◆── ExecutorService` usan **composición** (rombo negro relleno).
- [ ] Todas las asociaciones muestran **multiplicidades** (`1`, `0..*`, `2`) y **roles** cuando hay ambigüedad (`origen`/`destino`).
- [ ] Exportado como PDF con el nombre exacto: `DIAGRAMA_GRUPO_7.pdf`.

---

## Tarea 6 — William Melgar
**CIF:** 2026011736 · **Archivo:** `src/main/java/com/sv/grupo7/banco/implementation/LocalDeposit.java`

### 🎯 Lo que te toca
Implementar `LocalDeposit`: una `Transaction` que hace un **depósito sincronizado** sobre una `BankAccount`. Siempre exitoso si el monto es positivo. Retorna un `Receipt` con estado `SUCCESS`.

### 🧠 Por qué es importante
Aunque es la operación más simple, es clave para probar que el `deposit()` de `BankAccount` está correctamente sincronizado — si no lo estuviera, verías **pérdida de actualizaciones** (lost updates) cuando 100 hilos depositan al mismo tiempo. Aprendes a razonar sobre concurrencia incluso en operaciones triviales.

### 💼 Aplicación profesional real
- **Sistemas de recompensas**: acumular puntos, millas, cashback (siempre atómico y auditado).
- **Créditos a wallets** (PayPal, Wise, Mercado Pago).
- **Contadores concurrentes** en analytics (impresiones, clics, likes).

### 🔁 Cómo lo puedes reaprovechar
Toda operación de **incremento seguro** en un contador o saldo compartido entre hilos.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-06-melgar
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/implementation/` → clic derecho → `New → Java Class` → `LocalDeposit`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/implementation/` → `New File` → `LocalDeposit.java`.

**Paso 3 — Pegar el código** de la sección de abajo.

**Paso 4 — Verificar sintaxis.** Errores de clases ausentes son normales.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/implementation/LocalDeposit.java
git commit -m "feat(tarea06): implementar LocalDeposit (depósito sincronizado)"
git push -u origin feature/tarea-06-melgar
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 6 (LocalDeposit.java), push a feature/tarea-06-melgar"

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.implementation;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.BankAccount;
import com.sv.grupo7.banco.entities.Receipt;

import java.time.LocalDateTime;

public class LocalDeposit extends Transaction {

    private final Bank bank;
    private final BankAccount account;

    public LocalDeposit(Bank bank, BankAccount account, double amount) {
        super("DEPOSIT_LOCAL", amount);
        this.bank = bank;
        this.account = account;
    }

    @Override
    public Bank getOriginBank() {
        return bank;
    }

    @Override
    public Receipt call() {
        System.out.printf("[%s] %s → Depósito $%.2f en %s (cliente %s)%n",
                Thread.currentThread().getName(), transactionId,
                amount, account.getAccountNumber(), account.getClientId());

        account.deposit(amount);

        System.out.printf("[%s] %s → SUCCESS%n",
                Thread.currentThread().getName(), transactionId);

        return new Receipt(
                transactionId, type,
                null, account.getClientId(),
                null, account.getAccountNumber(),
                null, bank.getName(),
                amount, LocalDateTime.now(), "SUCCESS"
        );
    }
}
```

---

## Tarea 7 — Alejandro Merino
**CIF:** 2026020122 · **Archivo:** `src/main/java/com/sv/grupo7/banco/entities/BankAccount.java`

### 🎯 Lo que te toca
Implementar la **cuenta bancaria sincronizada**. Debe tener número de cuenta, referencia al cliente titular (por `clientId`), nombre del banco donde reside, y saldo. Los métodos `withdraw()`, `deposit()` y `getBalance()` deben ser **`synchronized`** para garantizar exclusión mutua.

### 🧠 Por qué es importante
Estás implementando el **corazón de la exclusión mutua** en Java. Si `withdraw()` no fuera `synchronized`, dos hilos podrían pasar simultáneamente la validación `balance >= amount` y ambos retirarían — provocando **balance negativo** (race condition clásica). Este es el ejercicio fundacional de todo curso de concurrencia.

### 💼 Aplicación profesional real
- **Todo sistema transaccional** (bancos, brokers, exchanges de criptomonedas) implementa exclusión mutua similar, aunque en producción se usen locks distribuidos (Redis, Zookeeper) o transacciones ACID de BD.
- **Contadores compartidos** en aplicaciones web (por eso Java tiene `AtomicInteger`).
- **Caches con TTL**: coordinar lectura/escritura sin corromper.

### 🔁 Cómo lo puedes reaprovechar
Cualquier recurso mutable compartido entre hilos: buffers, colas custom, gestores de sesiones.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-07-merino
```

**Paso 2 — Crear el archivo en tu IDE**
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/entities/` → clic derecho → `New → Java Class` → `BankAccount`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/entities/` → `New File` → `BankAccount.java`.

**Paso 3 — Pegar el código** de la sección de abajo.

**Paso 4 — Verificar sintaxis.**
Esta clase no depende de ninguna otra del proyecto, así que debería compilar completamente sin errores. En IntelliJ: `Ctrl+F9` → no debe haber subrayados rojos.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/entities/BankAccount.java
git commit -m "feat(tarea07): BankAccount con métodos synchronized"
git push -u origin feature/tarea-07-merino
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 7 (BankAccount.java), push a feature/tarea-07-merino"

### 📝 Código de referencia
```java
package com.sv.grupo7.banco.entities;

public class BankAccount {

    private final String accountNumber;
    private final String clientId;
    private final String bankName;
    private double balance;

    public BankAccount(String accountNumber, String clientId, String bankName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.bankName = bankName;
        this.balance = initialBalance;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public String getBankName() {
        return bankName;
    }
}
```

---

## Tarea 8 — Erika Fuentes
**CIF:** 2026011709 · **Archivos:** `src/test/java/com/sv/grupo7/banco/BankAccountTest.java` y `TransactionTest.java`

### 🎯 Lo que te toca
Escribir **pruebas unitarias con JUnit 5** que demuestren que la exclusión mutua funciona (no hay race conditions) y que el rollback de `InterBankTransfer` opera correctamente cuando la acreditación falla.

### 🧠 Por qué es importante
Probar código concurrente es difícil — un test que "pasa a veces" es peor que no probar. Aprendes a diseñar tests que **exponen race conditions** lanzando muchos hilos y verificando invariantes globales (ej. "la suma total del sistema no cambia después de N transferencias").

### 💼 Aplicación profesional real
- **CI/CD**: ninguna PR se mergea si los tests concurrentes fallan.
- **Empresas fintech** exigen coverage del 80%+ en lógica financiera crítica.
- **Herramientas de fuzz testing** (jcstress) para código lock-free.

### 🔁 Cómo lo puedes reaprovechar
Cualquier proyecto Java con lógica de negocio importante debe tener suite de JUnit — el patrón es idéntico.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-08-fuentes
```

**Paso 2 — Crear los archivos de test en tu IDE**

Los archivos van en la carpeta de TESTS, no de sources:

- **IntelliJ:**
  1. En el panel Project, navega hasta `src/test/java/com/sv/grupo7/banco/`.
  2. Clic derecho → `New → Java Class` → `BankAccountTest` → Enter.
  3. Repite para `TransactionTest`.

- **VS Code:**
  1. En el Explorer, navega hasta `src/test/java/com/sv/grupo7/banco/`.
  2. `New File` → `BankAccountTest.java`.
  3. `New File` → `TransactionTest.java`.

**Paso 3 — Pegar el código** en cada archivo según la sección de abajo.

**Paso 4 — Verificar sintaxis.**
Los tests dependen de JUnit 5 y de las clases de tus compañeros. Es normal que haya errores de "cannot find symbol" hasta que el proyecto esté integrado. Verifica que no haya errores de sintaxis propios de tus archivos.

**Paso 5 — Commit y push**
```bash
git add src/test/java/com/sv/grupo7/banco/BankAccountTest.java
git add src/test/java/com/sv/grupo7/banco/TransactionTest.java
git commit -m "test(tarea08): pruebas unitarias para BankAccount y Transaction"
git push -u origin feature/tarea-08-fuentes
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 8 (BankAccountTest + TransactionTest), push a feature/tarea-08-fuentes"

### 📝 Código de referencia — `BankAccountTest.java`
```java
package com.sv.grupo7.banco;

import com.sv.grupo7.banco.entities.BankAccount;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void withdrawFallaCuandoNoHayFondos() {
        BankAccount acc = new BankAccount("A-001", "C01", "Banco X", 100.0);
        assertFalse(acc.withdraw(150.0));
        assertEquals(100.0, acc.getBalance());
    }

    @Test
    void depositoAumentaSaldo() {
        BankAccount acc = new BankAccount("A-002", "C01", "Banco X", 100.0);
        acc.deposit(50);
        assertEquals(150.0, acc.getBalance());
    }

    @Test
    void concurrencia1000RetirosNoCorrompeSaldo() throws InterruptedException {
        BankAccount acc = new BankAccount("A-003", "C01", "Banco X", 10_000.0);
        ExecutorService pool = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 1000; i++) {
            pool.submit(() -> acc.withdraw(10));
        }
        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));

        // Con exclusión mutua: exactamente 1000 retiros de $10 = $10,000 → saldo debe quedar en 0
        assertEquals(0.0, acc.getBalance(), 0.001);
    }
}
```

### 📝 Código de referencia — `TransactionTest.java`
```java
package com.sv.grupo7.banco;

import com.sv.grupo7.banco.entities.*;
import com.sv.grupo7.banco.implementation.LocalDeposit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void depositoRetornaReceiptExitoso() throws Exception {
        Bank bank = new Bank("Banco Test", 2);
        BankAccount acc = new BankAccount("A-100", "C01", "Banco Test", 500.0);
        bank.addAccount(acc);

        Receipt r = new LocalDeposit(bank, acc, 200).call();

        assertEquals("SUCCESS", r.status());
        assertEquals(700.0, acc.getBalance());
        bank.shutdown();
    }
}
```

---

## Tarea 9 — Walter Vásquez
**CIF:** 2026010068 · **Archivos:** `src/main/java/com/sv/grupo7/banco/dao/DaoTransaction.java`, `src/main/java/com/sv/grupo7/banco/interfaces/IDao.java` y `src/main/java/com/sv/grupo7/banco/controller/MovementsViewer.java`

### 🎯 Lo que te toca
Tres piezas:
1. **`IDao<T>`** — interfaz genérica con `insert()` y `readAll()`.
2. **`DaoTransaction`** — implementa `IDao<Receipt>`. Serializa cada `Receipt` a un archivo `.dat` bajo `transacciones_finalizadas/`. Ofrece `historyFor(clientId)` y `printHistoryFor(clientId)` para filtrar movimientos por cliente.
3. **`MovementsViewer`** — ventana Swing (`JFrame + JTable + JComboBox`) que permite ver los movimientos filtrados por cliente y por banco.

### 🧠 Por qué es importante
Aprendes tres cosas clave: **serialización de objetos**, **patrón DAO** (separar lógica de acceso a datos del dominio), y **UI de escritorio con Swing** (que aunque ya no es lo más moderno, sigue siendo estándar en apps corporativas Java).

### 💼 Aplicación profesional real
- **DAO**: prácticamente todo backend Java (Spring Data, Hibernate, JOOQ) es una evolución del patrón DAO.
- **Serialización binaria**: cachés locales, formatos de intercambio entre servicios, snapshots de estado.
- **Swing**: bancos, universidades y gobiernos aún operan aplicaciones críticas en Swing/JavaFX.

### 🔁 Cómo lo puedes reaprovechar
- El `DaoTransaction` es plantilla para cualquier repositorio "en disco".
- El `MovementsViewer` es plantilla para cualquier visor tabular filtrable.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-09-vasquez
```

**Paso 2 — Crear los tres archivos en tu IDE**

Archivo 1 — `IDao.java` en el paquete `interfaces`:
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/interfaces/` → clic derecho → `New → Java Class` → en el tipo elige `Interface` → nombre `IDao`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/interfaces/` → `New File` → `IDao.java`.

Archivo 2 — `DaoTransaction.java` en el paquete `dao`:
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/dao/` → `New → Java Class` → `DaoTransaction`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/dao/` → `New File` → `DaoTransaction.java`.

Archivo 3 — `MovementsViewer.java` en el paquete `controller`:
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/controller/` → `New → Java Class` → `MovementsViewer`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/controller/` → `New File` → `MovementsViewer.java`.

Archivo 4 (opcional pero recomendado) — `DaoTransactionTest.java` en `src/test/`:
- Misma lógica, en `src/test/java/com/sv/grupo7/banco/`.

**Paso 3 — Pegar el código** en cada archivo según las secciones de abajo.

**Paso 4 — Verificar sintaxis** de cada archivo por separado.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/dao/DaoTransaction.java
git add src/main/java/com/sv/grupo7/banco/interfaces/IDao.java
git add src/main/java/com/sv/grupo7/banco/controller/MovementsViewer.java
git add src/test/java/com/sv/grupo7/banco/DaoTransactionTest.java
git commit -m "feat(tarea09): DAO de transacciones + visor Swing de movimientos"
git push -u origin feature/tarea-09-vasquez
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 9 (DaoTransaction + IDao + MovementsViewer), push a feature/tarea-09-vasquez"

### 📝 Código de referencia — `IDao.java`
```java
package com.sv.grupo7.banco.interfaces;

import java.util.List;

public interface IDao<T> {
    void insert(T item);
    List<T> readAll();
}
```

### 📝 Código de referencia — `DaoTransaction.java`
```java
package com.sv.grupo7.banco.dao;

import com.sv.grupo7.banco.entities.Receipt;
import com.sv.grupo7.banco.interfaces.IDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoTransaction implements IDao<Receipt> {

    private static final String DIR = "transacciones_finalizadas";

    public DaoTransaction() {
        File d = new File(DIR);
        if (!d.exists()) d.mkdirs();
    }

    @Override
    public synchronized void insert(Receipt receipt) {
        String path = DIR + File.separator + "receta_" + receipt.transactionId() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(receipt);
        } catch (IOException e) {
            System.err.println("[DaoTransaction] Error guardando " + path + ": " + e.getMessage());
        }
    }

    @Override
    public List<Receipt> readAll() {
        List<Receipt> all = new ArrayList<>();
        File dir = new File(DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));
        if (files == null) return all;
        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                all.add((Receipt) ois.readObject());
            } catch (Exception e) {
                System.err.println("[DaoTransaction] Error leyendo " + f.getName() + ": " + e.getMessage());
            }
        }
        return all;
    }

    public List<Receipt> historyFor(String clientId) {
        return readAll().stream()
                .filter(r -> clientId.equals(r.clientIdOrigen()) || clientId.equals(r.clientIdDestino()))
                .toList();
    }

    public void printHistoryFor(String clientId) {
        List<Receipt> h = historyFor(clientId);
        System.out.printf("%n--- Historial del cliente %s (%d movimientos) ---%n", clientId, h.size());
        h.forEach(r -> System.out.printf("  %s | %s | $%.2f | %s%n",
                r.timestamp(), r.type(), r.amount(), r.status()));
    }
}
```

### 📝 Código de referencia — `MovementsViewer.java`
```java
package com.sv.grupo7.banco.controller;

import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.Receipt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MovementsViewer extends JFrame {

    private final DaoTransaction dao = new DaoTransaction();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Timestamp", "Tipo", "Cliente Orig", "Cliente Dest",
                         "Cuenta Orig", "Cuenta Dest", "Banco Orig", "Banco Dest", "Monto", "Estado"}, 0);

    public MovementsViewer() {
        super("Multibanco G7 — Visor de Movimientos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 500);
        setLayout(new BorderLayout());

        List<Receipt> all = dao.readAll();

        JComboBox<String> filtroCliente = new JComboBox<>();
        filtroCliente.addItem("TODOS");
        all.stream()
                .flatMap(r -> java.util.stream.Stream.of(r.clientIdOrigen(), r.clientIdDestino()))
                .filter(java.util.Objects::nonNull)
                .distinct().sorted()
                .forEach(filtroCliente::addItem);

        JComboBox<String> filtroBanco = new JComboBox<>();
        filtroBanco.addItem("TODOS");
        all.stream()
                .flatMap(r -> java.util.stream.Stream.of(r.bancoOrigen(), r.bancoDestino()))
                .filter(java.util.Objects::nonNull)
                .distinct().sorted()
                .forEach(filtroBanco::addItem);

        JPanel top = new JPanel();
        top.add(new JLabel("Cliente:"));
        top.add(filtroCliente);
        top.add(new JLabel("Banco:"));
        top.add(filtroBanco);
        JButton refresh = new JButton("Filtrar");
        top.add(refresh);
        add(top, BorderLayout.NORTH);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        Runnable reload = () -> {
            model.setRowCount(0);
            String cli   = (String) filtroCliente.getSelectedItem();
            String banco = (String) filtroBanco.getSelectedItem();
            List<Receipt> filtered = dao.readAll().stream()
                    .filter(r -> "TODOS".equals(cli)   || cli.equals(r.clientIdOrigen())   || cli.equals(r.clientIdDestino()))
                    .filter(r -> "TODOS".equals(banco) || banco.equals(r.bancoOrigen())    || banco.equals(r.bancoDestino()))
                    .collect(Collectors.toList());
            for (Receipt r : filtered) {
                model.addRow(new Object[]{
                        r.timestamp(), r.type(),
                        r.clientIdOrigen(), r.clientIdDestino(),
                        r.cuentaOrigen(), r.cuentaDestino(),
                        r.bancoOrigen(), r.bancoDestino(),
                        String.format("$%.2f", r.amount()), r.status()
                });
            }
        };

        refresh.addActionListener(e -> reload.run());
        reload.run();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovementsViewer().setVisible(true));
    }
}
```

### 📝 Prueba mínima — `DaoTransactionTest.java`
```java
package com.sv.grupo7.banco;

import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.Receipt;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DaoTransactionTest {

    @Test
    void insertYReadAllPersisteReceipt() {
        DaoTransaction dao = new DaoTransaction();
        Receipt r = new Receipt("TEST0001", "TEST", "C01", "C02", "A-1", "A-2",
                "Banco X", "Banco Y", 100.0, LocalDateTime.now(), "SUCCESS");
        dao.insert(r);
        assertTrue(dao.readAll().stream().anyMatch(x -> "TEST0001".equals(x.transactionId())));
    }
}
```

---

## Tarea 10 — Carlos Ventura
**CIF:** 2026011585 · **Archivos:** `src/main/java/com/sv/grupo7/banco/implementation/InterBankTransfer.java` y `LocalTransfer.java`

### 🎯 Lo que te toca
Dos transacciones de la familia "transferencia":
1. **`LocalTransfer`** — mueve dinero entre 2 cuentas del **mismo banco**. Withdraw + deposit atómicos. Sin rollback.
2. **`InterBankTransfer`** — mueve dinero entre 2 cuentas de **bancos distintos**. Withdraw en origen → simular latencia y **85% probabilidad de éxito** → si falla la acreditación, **rollback** reingresando los fondos al origen.

### 🧠 Por qué es importante
Estás implementando el patrón **Saga** (compensating transactions) — clave en microservicios distribuidos donde no puedes hacer una transacción ACID clásica. Cuando algo falla en el paso N, deshaces los pasos 1..N-1 con operaciones compensatorias.

### 💼 Aplicación profesional real
- **Sagas en microservicios** (Kafka + Choreography-based Saga, o Temporal.io).
- **Sistemas de pagos**: si el cobro pasó pero el envío de la orden falla → refund automático.
- **Operaciones distribuidas** en bases NoSQL (Cassandra, DynamoDB) donde no hay transacciones ACID cross-partition.

### 🔁 Cómo lo puedes reaprovechar
Cualquier flujo multi-paso donde debas garantizar consistencia sin transacciones de BD: e-commerce, reservas de viaje, procesos ETL con checkpoints.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-10-ventura
```

**Paso 2 — Crear los dos archivos en tu IDE**

Archivo 1 — `LocalTransfer.java`:
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/implementation/` → `New → Java Class` → `LocalTransfer`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/implementation/` → `New File` → `LocalTransfer.java`.

Archivo 2 — `InterBankTransfer.java` (mismo paquete):
- **IntelliJ:** mismo directorio → `New → Java Class` → `InterBankTransfer`.
- **VS Code:** `New File` → `InterBankTransfer.java`.

**Paso 3 — Pegar el código** en cada archivo según las secciones de abajo.

**Paso 4 — Verificar sintaxis** de ambos archivos.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/implementation/LocalTransfer.java
git add src/main/java/com/sv/grupo7/banco/implementation/InterBankTransfer.java
git commit -m "feat(tarea10): transferencias local e interbancaria con rollback"
git push -u origin feature/tarea-10-ventura
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 10 (LocalTransfer + InterBankTransfer), push a feature/tarea-10-ventura"

### 📝 Código de referencia — `LocalTransfer.java`
```java
package com.sv.grupo7.banco.implementation;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.BankAccount;
import com.sv.grupo7.banco.entities.Receipt;

import java.time.LocalDateTime;

public class LocalTransfer extends Transaction {

    private final Bank bank;
    private final BankAccount origen;
    private final BankAccount destino;

    public LocalTransfer(Bank bank, BankAccount origen, BankAccount destino, double amount) {
        super("TRANSFER_LOCAL", amount);
        this.bank = bank;
        this.origen = origen;
        this.destino = destino;
    }

    @Override
    public Bank getOriginBank() {
        return bank;
    }

    @Override
    public Receipt call() {
        System.out.printf("[%s] %s → Transferencia intramuros $%.2f de %s a %s%n",
                Thread.currentThread().getName(), transactionId,
                amount, origen.getAccountNumber(), destino.getAccountNumber());

        boolean ok = origen.withdraw(amount);
        String status;
        if (ok) {
            destino.deposit(amount);
            status = "SUCCESS";
        } else {
            status = "FAILED";
        }

        System.out.printf("[%s] %s → %s%n",
                Thread.currentThread().getName(), transactionId, status);

        return new Receipt(
                transactionId, type,
                origen.getClientId(), destino.getClientId(),
                origen.getAccountNumber(), destino.getAccountNumber(),
                bank.getName(), bank.getName(),
                amount, LocalDateTime.now(), status
        );
    }
}
```

### 📝 Código de referencia — `InterBankTransfer.java`
```java
package com.sv.grupo7.banco.implementation;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.BankAccount;
import com.sv.grupo7.banco.entities.Receipt;

import java.time.LocalDateTime;
import java.util.Random;

public class InterBankTransfer extends Transaction {

    private final Bank bancoOrigen;
    private final Bank bancoDestino;
    private final BankAccount cuentaOrigen;
    private final BankAccount cuentaDestino;
    private final Random random = new Random();

    public InterBankTransfer(Bank bancoOrigen, Bank bancoDestino,
                             BankAccount cuentaOrigen, BankAccount cuentaDestino, double amount) {
        super("TRANSFER_INTERBANK", amount);
        this.bancoOrigen = bancoOrigen;
        this.bancoDestino = bancoDestino;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public Bank getOriginBank() {
        return bancoOrigen;
    }

    @Override
    public Receipt call() throws Exception {
        System.out.printf("[%s] %s → Interbancaria $%.2f: %s (%s) → %s (%s)%n",
                Thread.currentThread().getName(), transactionId, amount,
                cuentaOrigen.getAccountNumber(), bancoOrigen.getName(),
                cuentaDestino.getAccountNumber(), bancoDestino.getName());

        // 1. Retiro en origen
        if (!cuentaOrigen.withdraw(amount)) {
            System.err.printf("[%s] %s → FAILED (fondos insuficientes)%n",
                    Thread.currentThread().getName(), transactionId);
            return buildReceipt("FAILED");
        }

        // 2. Simular latencia de red + 85% éxito en destino
        Thread.sleep(150 + random.nextInt(250));
        boolean acreditacionOk = random.nextDouble() > 0.15;

        if (acreditacionOk) {
            cuentaDestino.deposit(amount);
            System.out.printf("[%s] %s → SUCCESS%n",
                    Thread.currentThread().getName(), transactionId);
            return buildReceipt("SUCCESS");
        } else {
            // 3. Rollback: reingresar al origen
            cuentaOrigen.deposit(amount);
            System.err.printf("[%s] %s → ROLLED_BACK (destino no respondió, fondos restituidos)%n",
                    Thread.currentThread().getName(), transactionId);
            return buildReceipt("ROLLED_BACK");
        }
    }

    private Receipt buildReceipt(String status) {
        return new Receipt(
                transactionId, type,
                cuentaOrigen.getClientId(), cuentaDestino.getClientId(),
                cuentaOrigen.getAccountNumber(), cuentaDestino.getAccountNumber(),
                bancoOrigen.getName(), bancoDestino.getName(),
                amount, LocalDateTime.now(), status
        );
    }
}
```

---

## Tarea 11 — Nicole Sánchez
**CIF:** 2026010813 · **Archivos:** `src/main/java/com/sv/grupo7/banco/entities/Receipt.java` y `Client.java`

### 🎯 Lo que te toca
Dos entidades:
1. **`Client`** — cliente ficticio con `id`, `name`, `lastName`.
2. **`Receipt`** — comprobante inmutable de una transacción, **serializable**, implementado como `record` de Java 21. Debe contener: `transactionId`, `type`, `clientIdOrigen/Destino`, `cuentaOrigen/Destino`, `bancoOrigen/Destino`, `amount`, `timestamp`, `status`.

### 🧠 Por qué es importante
Aprendes dos cosas: **serialización con `Serializable`** (necesaria para persistir a `.dat`) y los **`record`s de Java 21** — la forma moderna de escribir clases inmutables sin boilerplate. Un `record` te da constructor, getters, `equals()`, `hashCode()` y `toString()` gratis.

### 💼 Aplicación profesional real
- **DTOs y VOs**: los `record` son ideales para transferencia de datos entre capas (controller → service → dao).
- **Eventos en Event Sourcing**: los eventos son inmutables por definición — `record` es perfecto.
- **Modelos financieros**: comprobantes, tickets, facturas — todos son inmutables por regulación.

### 🔁 Cómo lo puedes reaprovechar
Cualquier objeto de datos que no debe mutar: eventos de dominio, respuestas de API, filas de reportes.

### ✅ Pasos para completar esta tarea

**Paso 1 — Crear la rama**
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tarea-11-sanchez
```

**Paso 2 — Crear los dos archivos en tu IDE**

Archivo 1 — `Receipt.java`:
- **IntelliJ:** `src/main/java/com/sv/grupo7/banco/entities/` → clic derecho → `New → Java Class` → en el tipo que aparece en el diálogo selecciona `Record` → nombre `Receipt`.
- **VS Code:** `src/main/java/com/sv/grupo7/banco/entities/` → `New File` → `Receipt.java`.

Archivo 2 — `Client.java` (mismo paquete):
- **IntelliJ:** mismo directorio → `New → Java Class` → `Client`.
- **VS Code:** `New File` → `Client.java`.

**Paso 3 — Pegar el código** en cada archivo según las secciones de abajo.

**Paso 4 — Verificar sintaxis.**
Estas dos clases no dependen de nada del proyecto (solo usan clases del JDK), así que deben compilar completamente sin errores. En IntelliJ: `Ctrl+F9` → sin errores rojos.

**Paso 5 — Commit y push**
```bash
git add src/main/java/com/sv/grupo7/banco/entities/Receipt.java
git add src/main/java/com/sv/grupo7/banco/entities/Client.java
git commit -m "feat(tarea11): entidades Receipt (record) y Client (Serializable)"
git push -u origin feature/tarea-11-sanchez
```

**Paso 6 — Avisar a Héctor:**
> "Héctor, listo Tarea 11 (Receipt + Client), push a feature/tarea-11-sanchez"

### 📝 Código de referencia — `Receipt.java`
```java
package com.sv.grupo7.banco.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Receipt(
        String transactionId,
        String type,
        String clientIdOrigen,
        String clientIdDestino,
        String cuentaOrigen,
        String cuentaDestino,
        String bancoOrigen,
        String bancoDestino,
        double amount,
        LocalDateTime timestamp,
        String status
) implements Serializable {

    @Override
    public String toString() {
        return String.format("[%s] %s | $%.2f | %s → %s | %s",
                timestamp, type, amount, bancoOrigen, bancoDestino, status);
    }
}
```

### 📝 Código de referencia — `Client.java`
```java
package com.sv.grupo7.banco.entities;

import java.io.Serializable;

public class Client implements Serializable {

    private final String id;
    private final String name;
    private final String lastName;

    public Client(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    @Override
    public String toString() {
        return id + " — " + getFullName();
    }
}
```
