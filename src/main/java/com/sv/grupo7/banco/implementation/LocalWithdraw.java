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
