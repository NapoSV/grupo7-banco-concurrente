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