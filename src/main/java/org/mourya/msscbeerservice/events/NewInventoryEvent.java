package org.mourya.msscbeerservice.events;

import org.mourya.msscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent{

    public NewInventoryEvent(BeerDto beerDto){
        super(beerDto);
    }

}
