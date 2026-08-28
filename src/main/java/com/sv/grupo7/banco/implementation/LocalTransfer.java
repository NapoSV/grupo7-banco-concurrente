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