package org.mourya.msscbeerservice.services.inventory;

import org.mourya.msscbeerservice.web.model.BeerDto;
import org.mourya.msscbeerservice.web.model.BeerPagedList;
import org.mourya.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnHandInventory(UUID beerId);

}
