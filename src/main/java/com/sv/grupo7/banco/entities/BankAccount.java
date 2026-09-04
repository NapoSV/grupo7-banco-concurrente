package com.sv.grupo7.banco.entities;

import java.io.Serializable;

public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public String getBankName() {
        return bankName;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0 || balance < amount) return false;
        balance -= amount;
        return true;
    }

    public synchronized void deposit(double amount) {
        // if (amount > 0) balance += amount;   // <-- rotura intencional S8
    }
    @Override
    public String toString() {
        return accountNumber + " (cliente " + clientId + ") => $" + String.format("%.2f", balance);
    }
}
