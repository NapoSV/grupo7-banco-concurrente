package com.sv.grupo7.banco.dao;

import com.sv.grupo7.banco.entities.Receipt;
import com.sv.grupo7.banco.interfaces.IDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoTransaction implements IDao<Receipt> {

    private static final String DIR = "transacciones_finalizadas";

    public DaoTransaction() {
        File d = new File(DIR);
        if (!d.exists()) d.mkdirs();
    }

    @Override
    public synchronized void insert(Receipt receipt) {
        String path = DIR + File.separator + "receta_" + receipt.transactionId() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(receipt);
        } catch (IOException e) {
            System.err.println("[DaoTransaction] Error guardando " + path + ": " + e.getMessage());
        }
    }

    @Override
    public List<Receipt> readAll() {
        List<Receipt> all = new ArrayList<>();
        File dir = new File(DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));
        if (files == null) return all;
        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                all.add((Receipt) ois.readObject());
            } catch (Exception e) {
                System.err.println("[DaoTransaction] Error leyendo " + f.getName() + ": " + e.getMessage());
            }
        }
        return all;
    }

    public List<Receipt> historyFor(String clientId) {
        return readAll().stream()
                .filter(r -> clientId.equals(r.clientIdOrigen()) || clientId.equals(r.clientIdDestino()))
                .toList();
    }

    public void printHistoryFor(String clientId) {
        List<Receipt> h = historyFor(clientId);
        System.out.printf("%n--- Historial del cliente %s (%d movimientos) ---%n", clientId, h.size());
        h.forEach(r -> System.out.printf("  %s | %s | $%.2f | %s%n",
                r.timestamp(), r.type(), r.amount(), r.status()));
    }
}