package com.dmo.mapstruct;

import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@AutoMap(targetType = CarVO.class)
@AllArgsConstructor
@Data
public class CarDTO {

    private String name;

    private CarType type;

    private int size;

}
