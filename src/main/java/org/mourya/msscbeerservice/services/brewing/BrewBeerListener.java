package org.mourya.msscbeerservice.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mourya.msscbeerservice.config.JmsConfig;
import org.mourya.msscbeerservice.domain.Beer;
import org.mourya.common.events.BrewBeerEvent;
import org.mourya.common.events.NewInventoryEvent;
import org.mourya.msscbeerservice.repositories.BeerRepository;
import org.mourya.msscbeerservice.web.model.BeerDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listener(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        log.error(beerDto.toString());
        Optional<Beer> beer = beerRepository.findById(beerDto.getId());

        if(beer.isEmpty()){
            log.error("Beer Object Can't Fetched");
        }else{
            log.warn("Fetched Beer : " + beer.get());
            beerDto.setQuantityOnHand(beer.get().getQuantityToBrew());

            NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

            log.error("Brewed beer " + beer.get().getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

            jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
        }


    }

}
