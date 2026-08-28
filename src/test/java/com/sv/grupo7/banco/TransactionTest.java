package com.sv.grupo7.banco;

import com.sv.grupo7.banco.entities.Bank;
import com.sv.grupo7.banco.entities.BankAccount;
import com.sv.grupo7.banco.entities.Receipt;
import com.sv.grupo7.banco.implementation.LocalWithdraw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Bank bank;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        bank = new Bank("BancoTest", 5);
        account = new BankAccount("ACC-TEST", "CLI-TEST", "BancoTest", 500.0);
        bank.addAccount(account);
    }

    @Test
    void testTipoTransaccion() {
        LocalWithdraw lw = new LocalWithdraw(bank, account, 100.0);
        assertEquals("WITHDRAW_LOCAL", lw.getType());
    }

    @Test
    void testMontoTransaccion() {
        LocalWithdraw lw = new LocalWithdraw(bank, account, 200.0);
        assertEquals(200.0, lw.getAmount(), 0.01);
    }

    @Test
    void testIdTransaccionUnico() {
        LocalWithdraw lw1 = new LocalWithdraw(bank, account, 100.0);
        LocalWithdraw lw2 = new LocalWithdraw(bank, account, 100.0);
        assertNotEquals(lw1.getTransactionId(), lw2.getTransactionId());
    }

    @Test
    void testRetiroExitosoReduceSaldo() throws Exception {
        LocalWithdraw lw = new LocalWithdraw(bank, account, 300.0);
        Receipt receipt = lw.call();
        assertEquals("SUCCESS", receipt.status());
        assertEquals(200.0, account.getBalance(), 0.01);
    }

    @Test
    void testRetiroFallaSaldoInsuficiente() throws Exception {
        LocalWithdraw lw = new LocalWithdraw(bank, account, 1000.0);
        Receipt receipt = lw.call();
        assertEquals("FAILED", receipt.status());
        assertEquals(500.0, account.getBalance(), 0.01);
    }

    @Test
    void testBancoOrigenCorrecto() {
        LocalWithdraw lw = new LocalWithdraw(bank, account, 100.0);
        assertEquals(bank, lw.getOriginBank());
    }
}
