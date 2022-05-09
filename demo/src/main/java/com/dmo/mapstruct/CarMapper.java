package com.dmo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

//    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDTO carToCarDto(Car car);
}
