package org.mourya.msscbeerservice.services.inventory;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Profile("local-discovery")
public class BeerInventoryServiceFeign implements BeerInventoryService{

    @Autowired
    InventoryServiceFeignClient inventoryServiceFeignClient;

    @Autowired
    InventoryFailoverFeignClient inventoryFailoverFeignClient;

    @Override
    @CircuitBreaker(name = "inventory-fallback", fallbackMethod = "inventoryFallback")
    public Integer getOnHandInventory(UUID beerId) {
        ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnHandInventory(beerId);

        //sum from inventory list
        Integer onHand =  Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("Beer Id : " + beerId + "On hand is : " + onHand);

        return onHand;
    }

    public Integer inventoryFallback(Exception e){
        return Objects.requireNonNull(inventoryFailoverFeignClient.getOnHandInventory().getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }
}
