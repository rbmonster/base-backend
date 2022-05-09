package com.dmo.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@MapperConfig(componentModel = "spring")
@SpringMapperConfig(conversionServiceAdapterPackage = "com.dmo.mapstruct",
        conversionServiceAdapterClassName = "MapStructConversionServiceAdapter")
public class MapperSpringConfig {

}
