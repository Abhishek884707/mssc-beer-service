package org.mourya.msscbeerservice.services;

import lombok.RequiredArgsConstructor;
import org.mourya.msscbeerservice.domain.Beer;
import org.mourya.msscbeerservice.repositories.BeerRepository;
import org.mourya.msscbeerservice.web.controller.NotFoundException;
import org.mourya.msscbeerservice.web.mapper.BeerMapper;
import org.mourya.msscbeerservice.web.model.BeerDto;
import org.mourya.msscbeerservice.web.model.BeerPagedList;
import org.mourya.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;


    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyleEnum, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyleEnum, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyleEnum)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyleEnum, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(Boolean.TRUE.equals(showInventoryOnHand)){

            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .toList(),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else{
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .toList(),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand){
        if(Boolean.TRUE.equals(showInventoryOnHand)){
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }else{
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
    }

    public BeerDto saveNewBeer(BeerDto beerDto){
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    public BeerDto updateBeer(UUID beerId, BeerDto beerDto){
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

}
