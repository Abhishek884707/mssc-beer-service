package org.mourya.msscbeerservice.services.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient {

    @Autowired
    private InventoryFailoverFeignClient inventoryFailoverFeignClient;


    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
        return inventoryFailoverFeignClient.getOnHandInventory();
    }
}
