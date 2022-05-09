package com.dmo.mapstruct;

import lombok.Data;

@Data
public class Car {

    private String name;

    private CarType type;

    private int size;

    public Car(String name, CarType type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }
}


