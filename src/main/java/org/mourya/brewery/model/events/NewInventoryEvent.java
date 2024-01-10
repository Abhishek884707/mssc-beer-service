package org.mourya.brewery.model.events;

import lombok.NoArgsConstructor;
import org.mourya.brewery.model.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto){
        super(beerDto);
    }

}
