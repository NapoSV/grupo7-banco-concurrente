package com.sv.grupo7.banco;

import com.sv.grupo7.banco.entities.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC-001", "CLI-001", "BancoTest", 1000.0);
    }

    @Test
    void testSaldoInicial() {
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    void testDepositoExitoso() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }

    @Test
    void testRetiroExitoso() {
        boolean result = account.withdraw(300.0);
        assertTrue(result);
        assertEquals(700.0, account.getBalance(), 0.01);
    }

    @Test
    void testRetiroFondosInsuficientes() {
        boolean result = account.withdraw(2000.0);
        assertFalse(result);
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    void testRetiroMontoNegativo() {
        boolean result = account.withdraw(-100.0);
        assertFalse(result);
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    void testDepositoMontoNegativo() {
        account.deposit(-200.0);
        assertEquals(1000.0, account.getBalance(), 0.01);
    }

    @Test
    void testConcurrenciaMultiplesHilos() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> account.withdraw(50.0));
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        assertTrue(account.getBalance() >= 0.0);
        assertTrue(account.getBalance() <= 1000.0);
    }
}
