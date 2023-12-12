package org.mourya.msscbeerservice.services;

import org.mourya.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
