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