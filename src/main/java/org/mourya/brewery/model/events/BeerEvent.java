package org.mourya.brewery.model.events;

import lombok.*;
import org.mourya.brewery.model.BeerDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -6065908993135151342L;

    private BeerDto beerDto;
}
