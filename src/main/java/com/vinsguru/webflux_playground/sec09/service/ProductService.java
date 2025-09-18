package com.vinsguru.webflux_playground.sec09.service;

import com.vinsguru.webflux_playground.sec09.dto.ProductDto;
import com.vinsguru.webflux_playground.sec09.mapper.EntityDtoMapper;
import com.vinsguru.webflux_playground.sec09.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> saveProducts(Mono<ProductDto> mono){
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoMapper::toDTO)
                .doOnNext(dto -> this.sink.tryEmitNext(dto));
    }

    public Flux<ProductDto> productStream(){
        return this.sink.asFlux();
    }

}
