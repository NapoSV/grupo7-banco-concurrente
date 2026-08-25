# Guía Paso a Paso — Semana 7 · Multibanco Concurrente (Grupo 7)

**Universidad Evangélica de El Salvador** · Facultad de Ingeniería · **Programación II**
**Facilitador:** Ing. Daniel Enrique Guevara Gómez
**Deadline:** 🕛 **jueves 27/08/2026 a las 23:59**

---

## 📖 Cómo usar esta guía

1. Busca tu nombre en la **tabla de asignación** de abajo y haz clic en el enlace para saltar a tu tarea.
2. Lee la explicación completa (**qué haces**, **por qué es importante**, **aplicación profesional**, **reaprovechamiento**).
3. Copia el código de referencia en el archivo indicado. Puedes adaptarlo, pero **respeta las firmas públicas** para que el proyecto integre.
4. Sigue los **comandos Git** exactos al final de tu sección.
5. Avisa a Héctor por Teams cuando hayas hecho `push` — él abre la PR.

---

## 🧭 Tabla de asignación

| # | Integrante | Tarea | Ir a |
|---|---|---|---|
| 1 | LOPEZ RUIZ HECTOR NAPOLEON | `BankSimulation` + integración | [Tarea 1](#tarea-1--héctor-lópez) |
| 2 | VIGIL RAMIREZ ALEJANDRO ANTONIO | `Bank.java` | [Tarea 2](#tarea-2--alejandro-vigil) |
| 3 | ORELLANA ROJAS BAYRON ALEXANDER | `Transaction.java` (abstracta) | [Tarea 3](#tarea-3--bayron-orellana) |
| 4 | DIAZ SANTOS ZAIR BENETT | `LocalWithdraw.java` | [Tarea 4](#tarea-4--zair-díaz) |
| 5 | FLORES HERNANDEZ WALTER ALEJANDRO | `DIAGRAMA_GRUPO_7.pdf` (UML) | [Tarea 5](#tarea-5--walter-flores) |
| 6 | MELGAR RIVAS WILLIAM ARIEL | `LocalDeposit.java` | [Tarea 6](#tarea-6--william-melgar) |
| 7 | MERINO VENTURA ALEJANDRO SEBASTIAN | `BankAccount.java` | [Tarea 7](#tarea-7--alejandro-merino) |
| 8 | FUENTES ORTIZ ERIKA ALEXANDRA | Pruebas unitarias JUnit 5 | [Tarea 8](#tarea-8--erika-fuentes) |
| 9 | VASQUEZ AMAYA WALTER AMILCAR | `DaoTransaction` + `MovementsViewer` (Swing) | [Tarea 9](#tarea-9--walter-vásquez) |
| 10 | VENTURA VELASQUEZ CARLOS MARIO | `InterBankTransfer` + `LocalTransfer` | [Tarea 10](#tarea-10--carlos-ventura) |
| 11 | SANCHEZ MENJIVAR NICOLE NOHEMY | `Receipt` + `Client` | [Tarea 11](#tarea-11--nicole-sánchez) |

---

## 📏 Reglas para todo el equipo (léelas antes de empezar)

- **Java 21+ obligatorio** (Temurin/OpenJDK). Verifica con `java -version`.
- **Package raíz obligatorio:** `com.sv.grupo7.banco` (no lo cambies).
- **Nunca hagas merge** a `develop` ni a `main` — solo push a tu rama `feature/tarea-NN-<apellido>`. Las PRs las abre **Héctor**.
- **Respeta las firmas públicas** (constructor y métodos `public`) del código de referencia — el resto del equipo depende de ellas.
- No agregues secretos, contraseñas, IPs internas ni datos personales.
- Comentarios en español; nombres de variables en inglés (convención Java).
- Antes de commit: **compila localmente** para verificar que tu archivo no rompe nada.

---

## 🌿 Comandos Git base (aplican a todos)

```bash
# Una sola vez: clonar el repo
git clone https://github.com/NapoSV/grupo7-banco-concurrente.git TareaS7_Banco
cd TareaS7_Banco

# Cada vez que empieces a trabajar
git checkout develop
git pull origin develop
git checkout -b feature/tarea-NN-<tu-apellido>   # ej. feature/tarea-04-diaz

# ... editas tu archivo ...

# Commit y push
git add <ruta/de/tu/archivo>
git commit -m "feat(tareaNN): <descripción corta>"
git push -u origin feature/tarea-NN-<tu-apellido>

# Avisa a Héctor por Teams para que abra la PR
```

---

## Tarea 1 — Héctor López
**CIF:** 2026010132 · **Archivo:** `src/main/java/com/sv/grupo7/banco/controller/BankSimulation.java`

### 🎯 Lo que te toca
Escribir el **orquestador maestro** de la simulación: crear 3 bancos, ~20 clientes, distribuir cuentas entre ellos, generar un array de **100 transacciones aleatorias** de 4 tipos distintos, enviarlas a los `ExecutorService` de cada banco, recolectar los `Future<Receipt>`, persistirlos y mostrar la auditoría final. Además, tú te encargas de la **consolidación final** (mergear PRs, correr todo, empaquetar ZIP, exportar PDF).

### 🧠 Por qué es importante (aprendizaje)
Estás implementando un patrón **Producer-Consumer** con `ExecutorService` — el mismo que usan colas de mensajes (RabbitMQ, Kafka consumers), pools de workers HTTP, y sistemas de procesamiento batch. Aprendes a **coordinar múltiples pools de hilos independientes**, a **recolectar resultados asíncronos** con `Future.get()` y a hacer un **shutdown ordenado** para no perder trabajo en vuelo.

### 💼 Aplicación profesional real
- **Bancos reales**: cada motor transaccional (Swift, ACH, tarjetas) tiene su propio pool de hilos por región/canal. La orquestación luce igual.
- **APIs de alto tráfico**: Netflix, Uber, Spotify usan patrones idénticos con `CompletableFuture` para paralelizar llamadas a microservicios.
- **ETL de datos**: Airflow, Spark y Dagster corren tareas en paralelo con la misma estructura conceptual.

### 🔁 Cómo lo puedes reaprovechar
En cualquier proyecto donde necesites paralelizar N tareas independientes: procesamiento de imágenes, envío masivo de correos, scraping web, backtests financieros.

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
            int numCuentas = 1 + rnd.nextInt(2); // 1 o 2 cuentas
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-01-lopez
# ... crear el archivo ...
git add src/main/java/com/sv/grupo7/banco/controller/BankSimulation.java
git commit -m "feat(tarea01): orquestador BankSimulation con 100 hilos concurrentes"
git push -u origin feature/tarea-01-lopez
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-02-vigil
git add src/main/java/com/sv/grupo7/banco/entities/Bank.java
git commit -m "feat(tarea02): entidad Bank con ExecutorService propio"
git push -u origin feature/tarea-02-vigil
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

    /** Cada transacción debe indicar en qué banco se origina, para que el orquestador la envíe al pool correcto. */
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-03-orellana
git add src/main/java/com/sv/grupo7/banco/abstraction/Transaction.java
git commit -m "feat(tarea03): clase abstracta Transaction (Callable<Receipt>)"
git push -u origin feature/tarea-03-orellana
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-04-diaz
git add src/main/java/com/sv/grupo7/banco/implementation/LocalWithdraw.java
git commit -m "feat(tarea04): implementar LocalWithdraw (retiro sincronizado)"
git push -u origin feature/tarea-04-diaz
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

### 📝 Herramientas sugeridas
- **draw.io** (gratis, web): https://app.diagrams.net/ → `File → Export as → PDF`
- **PlantUML** (texto → diagrama): https://www.plantuml.com/plantuml/
- **Mermaid** (dentro de VS Code con extensión Markdown Preview Mermaid): más rápido para versionar en Git

### Estructura mínima que debe mostrar el diagrama

```
Bank ──1..*── BankAccount ──1──> Client
  │                              (clientId)
  └── contiene ExecutorService (composición)

Transaction (abstracta, Callable<Receipt>)
    ▲
    ├── LocalWithdraw       ──> BankAccount
    ├── LocalDeposit        ──> BankAccount
    ├── LocalTransfer       ──> 2× BankAccount (origen + destino, mismo Bank)
    └── InterBankTransfer   ──> 2× BankAccount (origen Bank A, destino Bank B)

DaoTransaction ──implements──> IDao<Receipt>
DaoTransaction ──persiste──> Receipt (Serializable)

BankSimulation ──crea──> Bank, Client, BankAccount
BankSimulation ──lanza──> 100× Transaction vía Bank.submit()
```

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-05-flores
# copia tu PDF (y opcional .drawio/.puml) en la carpeta docs/
git add docs/DIAGRAMA_GRUPO_7.pdf docs/DIAGRAMA_GRUPO_7.drawio
git commit -m "docs(tarea05): agregar diagrama UML del sistema Multibanco"
git push -u origin feature/tarea-05-flores
```

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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-06-melgar
git add src/main/java/com/sv/grupo7/banco/implementation/LocalDeposit.java
git commit -m "feat(tarea06): implementar LocalDeposit (depósito sincronizado)"
git push -u origin feature/tarea-06-melgar
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-07-merino
git add src/main/java/com/sv/grupo7/banco/entities/BankAccount.java
git commit -m "feat(tarea07): BankAccount con métodos synchronized"
git push -u origin feature/tarea-07-merino
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

### ⚙️ Ejecutar los tests
Si el proyecto usa Maven: `mvn test`
Si usa `javac` puro: compilar los tests con JUnit 5 en el classpath (Héctor definirá esto en integración).

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-08-fuentes
git add src/test/java/com/sv/grupo7/banco/
git commit -m "test(tarea08): pruebas unitarias para BankAccount y Transaction"
git push -u origin feature/tarea-08-fuentes
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
            new String[]{"Timestamp", "Tipo", "Cliente Orig", "Cliente Dest", "Cuenta Orig", "Cuenta Dest", "Banco Orig", "Banco Dest", "Monto", "Estado"}, 0);

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
                    .filter(r -> "TODOS".equals(cli) || cli.equals(r.clientIdOrigen()) || cli.equals(r.clientIdDestino()))
                    .filter(r -> "TODOS".equals(banco) || banco.equals(r.bancoOrigen()) || banco.equals(r.bancoDestino()))
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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-09-vasquez
git add src/main/java/com/sv/grupo7/banco/dao/ \
        src/main/java/com/sv/grupo7/banco/interfaces/ \
        src/main/java/com/sv/grupo7/banco/controller/MovementsViewer.java \
        src/test/java/com/sv/grupo7/banco/DaoTransactionTest.java
git commit -m "feat(tarea09): DAO de transacciones + visor Swing de movimientos"
git push -u origin feature/tarea-09-vasquez
```

---

## Tarea 10 — Carlos Ventura
**CIF:** 2026011585 · **Archivos:** `src/main/java/com/sv/grupo7/banco/implementation/InterBankTransfer.java` y `LocalTransfer.java`

### 🎯 Lo que te toca
Dos transacciones de la familia "transferencia":
1. **`LocalTransfer`** — mueve dinero entre 2 cuentas del **mismo banco**. Withdraw + deposit atómicos. Sin rollback.
2. **`InterBankTransfer`** — mueve dinero entre 2 cuentas de **bancos distintos**. Withdraw en origen → simular latencia y **85% probabilidad de éxito** → si falla la acreditación, **rollback** reingresando los fondos al origen.

### 🧠 Por qué es importante
Estás implementando el patrón **Saga** (compensating transactions) — clave en microservicios distribuidos donde no puedes hacer una transacción ACID clásica. Cuando algo falla en el paso N, deshaces los pasos 1..N-1 con operaciones compensatorias. Esto es lo que hace, por ejemplo, Airbnb cuando reserva alojamiento + cobro + notificación y algo falla a mitad del proceso.

### 💼 Aplicación profesional real
- **Sagas en microservicios** (Kafka + Choreography-based Saga, o Temporal.io).
- **Sistemas de pagos**: si el cobro pasó pero el envío de la orden falla → refund automático.
- **Operaciones distribuidas** en bases NoSQL (Cassandra, DynamoDB) donde no hay transacciones ACID cross-partition.

### 🔁 Cómo lo puedes reaprovechar
Cualquier flujo multi-paso donde debas garantizar consistencia sin transacciones de BD: e-commerce, reservas de viaje, procesos ETL con checkpoints.

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

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-10-ventura
git add src/main/java/com/sv/grupo7/banco/implementation/LocalTransfer.java \
        src/main/java/com/sv/grupo7/banco/implementation/InterBankTransfer.java
git commit -m "feat(tarea10): transferencias local e interbancaria con rollback"
git push -u origin feature/tarea-10-ventura
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

### 📝 Código de referencia — `Client.java`
```java
package com.sv.grupo7.banco.entities;

import java.io.Serializable;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String lastName;

    public Client(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getLastName() { return lastName; }
    public String getFullName() { return name + " " + lastName; }

    @Override
    public String toString() {
        return "Client{" + id + ": " + getFullName() + "}";
    }
}
```

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
        String status               // SUCCESS | FAILED | ROLLED_BACK
) implements Serializable {

    @Override
    public String toString() {
        return String.format("[%s] %s | %s → %s | $%.2f | %s",
                timestamp, transactionId,
                cuentaOrigen != null ? cuentaOrigen : "N/A",
                cuentaDestino != null ? cuentaDestino : "N/A",
                amount, status);
    }
}
```

### 🌿 Comandos Git
```bash
git checkout develop && git pull origin develop
git checkout -b feature/tarea-11-sanchez
git add src/main/java/com/sv/grupo7/banco/entities/Client.java \
        src/main/java/com/sv/grupo7/banco/entities/Receipt.java
git commit -m "feat(tarea11): entidades Client y Receipt (record serializable)"
git push -u origin feature/tarea-11-sanchez
```

---

## 🆘 ¿Tienes dudas?

- **Duda técnica** → escríbelo en el chat de Teams del Grupo 7.
- **No compila** → adjunta el error completo y la clase en el chat.
- **Git te da problemas** → primero `git status`, luego pega el output al chat.

**Recuerda:** la calidad de tu clase impacta al resto del equipo. Compila localmente antes de hacer push. 💪
