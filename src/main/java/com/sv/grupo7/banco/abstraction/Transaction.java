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