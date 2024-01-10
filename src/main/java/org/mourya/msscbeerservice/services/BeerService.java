package org.mourya.msscbeerservice.services;

import org.mourya.brewery.model.BeerDto;
import org.mourya.brewery.model.BeerPagedList;
import org.mourya.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyleEnum, PageRequest of, Boolean showInventoryOnHand);

    BeerDto getByUpc(String upc);
}
