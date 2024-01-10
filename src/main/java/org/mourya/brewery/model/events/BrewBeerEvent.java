package org.mourya.brewery.model.events;

import lombok.NoArgsConstructor;
import org.mourya.brewery.model.BeerDto;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto){
        super(beerDto);
    }

}

