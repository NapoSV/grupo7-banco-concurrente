package com.sv.grupo7.banco;

import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.Receipt;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DaoTransactionTest {

    @Test
    void insertYReadAllPersisteReceipt() {
        DaoTransaction dao = new DaoTransaction();
        Receipt r = new Receipt("TEST0001", "TEST", "C01", "C02", "A-1", "A-2",
                "Banco X", "Banco Y", 100.0, LocalDateTime.now(), "SUCCESS");
        dao.insert(r);
        assertTrue(dao.readAll().stream().anyMatch(x -> "TEST0001".equals(x.transactionId())));
    }
}