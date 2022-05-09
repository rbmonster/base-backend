package com.dmo.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarVO {

    private String name;

    private CarType type;

    private int size;

}
