package com.vinsguru.webflux_playground.sec07;


import com.vinsguru.webflux_playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec07BasicAuthTest extends AbstractWebClient {


    private static final Logger log = LoggerFactory.getLogger(Lec07BasicAuthTest.class);
    private WebClient client = createWebClient(b -> b.defaultHeaders(h -> h.setBasicAuth("java","secret")));


    @Test
    public void defaultHeader(){
        this.client.get()
                .uri("/lec07/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }



}
