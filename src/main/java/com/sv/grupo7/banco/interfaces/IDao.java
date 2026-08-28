package com.sv.grupo7.banco.interfaces;

import java.util.List;

public interface IDao<T> {
    void insert(T item);
    List<T> readAll();
}