package com.sv.grupo7.banco.entities;

import java.io.Serializable;

public class BankAccount implements Serializable {

    private final String accountNumber;
    private final String clientId;
    private double balance;

    public BankAccount(String accountNumber, String clientId, double initialBalance) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientId() {
        return clientId;
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
        if (amount > 0) balance += amount;
    }

    @Override
    public String toString() {
        return accountNumber + " (cliente " + clientId + ") => $" + String.format("%.2f", balance);
    }
}
