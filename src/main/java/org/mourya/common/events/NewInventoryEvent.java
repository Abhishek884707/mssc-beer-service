package org.mourya.common.events;

import lombok.NoArgsConstructor;
import org.mourya.msscbeerservice.web.model.BeerDto;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto){
        super(beerDto);
    }

}
