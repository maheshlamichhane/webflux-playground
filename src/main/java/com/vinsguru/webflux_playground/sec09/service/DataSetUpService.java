package com.vinsguru.webflux_playground.sec09.service;

import com.vinsguru.webflux_playground.sec09.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetUpService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("################");
        Flux.range(1,1000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ProductDto(null,"product-"+i, ThreadLocalRandom.current().nextInt(1,100)))
                .map(dto -> this.productService.saveProducts(Mono.just(dto)))
                .subscribe();
    }
}
