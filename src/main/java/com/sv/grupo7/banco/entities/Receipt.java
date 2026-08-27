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
        String status
) implements Serializable {

    @Override
    public String toString(){
        return String.format("[%s] %s | $s → %s | %s",
                timestamp, type, amount, bancoOrigen, bancoDestino, status);
    }

}
