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
        System.out.printf("[%s] %s → Depósito local $%.2f en %s (cliente %s)%n",
                Thread.currentThread().getName(), transactionId,
                amount, account.getAccountNumber(), account.getClientId());

        account.deposit(amount);

        // Bonificación del 1% si el saldo resultante supera $1 000
        if (account.getBalance() >= 1000.0) {
            double bonus = account.getBalance() * 0.01;
            account.deposit(bonus);
            System.out.printf("[%s] %s → Bonificación aplicada: +$%.2f%n",
                    Thread.currentThread().getName(), transactionId, bonus);
        }

        System.out.printf("[%s] %s → SUCCESS%n",
                Thread.currentThread().getName(), transactionId);

        return new Receipt(
                transactionId, type,
                account.getClientId(), null,
                account.getAccountNumber(), null,
                bank.getName(), null,
                amount, LocalDateTime.now(), "SUCCESS"
        );
    }
}
