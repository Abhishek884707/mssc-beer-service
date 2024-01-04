package org.mourya.common.events;

import lombok.NoArgsConstructor;
import org.mourya.msscbeerservice.web.model.BeerDto;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto){
        super(beerDto);
    }

}

