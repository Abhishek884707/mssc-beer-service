package org.mourya.msscbeerservice.services;

import lombok.RequiredArgsConstructor;
import org.mourya.msscbeerservice.domain.Beer;
import org.mourya.msscbeerservice.repositories.BeerRepository;
import org.mourya.msscbeerservice.web.controller.NotFoundException;
import org.mourya.msscbeerservice.web.mapper.BeerMapper;
import org.mourya.msscbeerservice.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    public BeerDto getById(UUID beerId){
        return beerMapper.BeerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    public BeerDto saveNewBeer(BeerDto beerDto){
        return beerMapper.BeerToBeerDto(beerRepository.save(beerMapper.BeerDtoToBeer(beerDto)));
    }

    public BeerDto updateBeer(UUID beerId, BeerDto beerDto){
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.BeerToBeerDto(beerRepository.save(beer));
    }
}
