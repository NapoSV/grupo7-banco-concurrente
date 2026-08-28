package com.sv.grupo7.banco.controller;

import com.sv.grupo7.banco.dao.DaoTransaction;
import com.sv.grupo7.banco.entities.Receipt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MovementsViewer extends JFrame {

    private final DaoTransaction dao = new DaoTransaction();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Timestamp", "Tipo", "Cliente Orig", "Cliente Dest",
                    "Cuenta Orig", "Cuenta Dest", "Banco Orig", "Banco Dest", "Monto", "Estado"}, 0);

    public MovementsViewer() {
        super("Multibanco G7 — Visor de Movimientos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 500);
        setLayout(new BorderLayout());

        List<Receipt> all = dao.readAll();

        JComboBox<String> filtroCliente = new JComboBox<>();
        filtroCliente.addItem("TODOS");
        all.stream()
                .flatMap(r -> java.util.stream.Stream.of(r.clientIdOrigen(), r.clientIdDestino()))
                .filter(java.util.Objects::nonNull)
                .distinct().sorted()
                .forEach(filtroCliente::addItem);

        JComboBox<String> filtroBanco = new JComboBox<>();
        filtroBanco.addItem("TODOS");
        all.stream()
                .flatMap(r -> java.util.stream.Stream.of(r.bancoOrigen(), r.bancoDestino()))
                .filter(java.util.Objects::nonNull)
                .distinct().sorted()
                .forEach(filtroBanco::addItem);

        JPanel top = new JPanel();
        top.add(new JLabel("Cliente:"));
        top.add(filtroCliente);
        top.add(new JLabel("Banco:"));
        top.add(filtroBanco);
        JButton refresh = new JButton("Filtrar");
        top.add(refresh);
        add(top, BorderLayout.NORTH);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        Runnable reload = () -> {
            model.setRowCount(0);
            String cli   = (String) filtroCliente.getSelectedItem();
            String banco = (String) filtroBanco.getSelectedItem();
            List<Receipt> filtered = dao.readAll().stream()
                    .filter(r -> "TODOS".equals(cli)   || cli.equals(r.clientIdOrigen())   || cli.equals(r.clientIdDestino()))
                    .filter(r -> "TODOS".equals(banco) || banco.equals(r.bancoOrigen())    || banco.equals(r.bancoDestino()))
                    .collect(Collectors.toList());
            for (Receipt r : filtered) {
                model.addRow(new Object[]{
                        r.timestamp(), r.type(),
                        r.clientIdOrigen(), r.clientIdDestino(),
                        r.cuentaOrigen(), r.cuentaDestino(),
                        r.bancoOrigen(), r.bancoDestino(),
                        String.format("$%.2f", r.amount()), r.status()
                });
            }
        };

        refresh.addActionListener(e -> reload.run());
        reload.run();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovementsViewer().setVisible(true));
    }
}