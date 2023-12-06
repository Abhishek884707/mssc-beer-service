package org.mourya.msscbeerservice.web.mapper;

import org.mapstruct.Mapper;
import org.mourya.msscbeerservice.domain.Beer;
import org.mourya.msscbeerservice.web.model.BeerDto;

@Mapper
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto beerDto);

}
