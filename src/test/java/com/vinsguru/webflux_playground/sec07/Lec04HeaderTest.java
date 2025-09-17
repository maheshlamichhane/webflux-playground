package com.vinsguru.webflux_playground.sec07;


import com.vinsguru.webflux_playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec04HeaderTest extends AbstractWebClient {


    private static final Logger log = LoggerFactory.getLogger(Lec04HeaderTest.class);
    private WebClient client = createWebClient(b -> b.defaultHeader("caller-id","order-service"));


    @Test
    public void defaultHeader(){
        this.client.get()
                .uri("/lec04/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void overrideHeader(){
        this.client.get()
                .uri("/lec04/product/{id}",1)
                .header("caller-id","new values")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void headersWithMap(){

        var map = Map.of(
                "caller-id","map value"
        );
        this.client.get()
                .uri("/lec04/product/{id}",1)
                .headers(h -> h.setAll(map))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }


}
