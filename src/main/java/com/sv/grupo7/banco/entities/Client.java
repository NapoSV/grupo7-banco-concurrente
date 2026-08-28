package com.sv.grupo7.banco.entities;

import java.io.Serializable;

public class Client implements Serializable {

    private final String id;
    private final String name;
    private final String lastName;

    public Client(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return name + " " + lastName;
    }

    @Override
    public String toString(){
        return id + " - " + getFullName();
    }

}
