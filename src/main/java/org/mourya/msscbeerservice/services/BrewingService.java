package org.mourya.msscbeerservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mourya.msscbeerservice.config.JmsConfig;
import org.mourya.msscbeerservice.domain.Beer;
import org.mourya.msscbeerservice.events.BrewBeerEvent;
import org.mourya.msscbeerservice.repositories.BeerRepository;
import org.mourya.msscbeerservice.services.inventory.BeerInventoryService;
import org.mourya.msscbeerservice.web.mapper.BeerMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper mapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());

            log.info("Min OnHand is : " + beer.getMinOnHand());
            log.info("Inventory is : "+ invQOH);

            if(beer.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(mapper.beerToBeerDto(beer)));
            }
        });
    }

}
