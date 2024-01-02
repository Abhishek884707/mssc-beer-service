package org.mourya.msscbeerservice.events;

import org.mourya.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto){
        super(beerDto);
    }

}

