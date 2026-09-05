package com.sv.grupo7.banco.controller;

import com.sv.grupo7.banco.abstraction.Transaction;
import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.*;
import com.sv.grupo7.banco.implementation.*;

import java.util.*;
import java.util.concurrent.Future;

public class BankSimulation {

    private static final int TOTAL_TRANSACCIONES = 0;

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
        if (list.isEmpty()) throw new IllegalStateException("El banco " + b.getName() + " no tiene cuentas registradas.");
        return list.get(rnd.nextInt(list.size()));
    }
}
