package com.vinsguru.webflux_playground.sec08;

import com.vinsguru.webflux_playground.sec08.mapper.EntityDtoMapper;
import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import com.vinsguru.webflux_playground.sec08.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ProductsUploadDownloadTest {

    private static final Logger log = LoggerFactory.getLogger(ProductsUploadDownloadTest.class);
    private final ProductClient client = new ProductClient();

    @Autowired
    private ProductRepository repository;

    @Test
    public void upload(){
        var flux = Flux.range(1,1_000_000)
                .map(i -> new ProductDto(null ,"product-"+i,i));

        this.client.uploadProducts(flux)
                .doOnNext(r -> log.info("received:{}",r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void downloadProducts(){
        this.client.downloadProducts()
                .doOnNext(r -> log.info("received:{}",r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
