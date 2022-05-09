package com.dmo.mapstruct;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarVOMapper {

    //    @Mapping(source = "numberOfSeats", target = "seatCount")
    Car carVOToCar(CarVO carVO);
}
