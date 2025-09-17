package com.vinsguru.webflux_playground.sec07;

import com.vinsguru.webflux_playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

public class Lec03PostTest extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(Lec02FluxTest.class);

    private final WebClient client = createWebClient();

    @Test
    public void postBodyValue(){
        var product = new Product(null,"demo product",200);
        this.client.post()
                .uri("/lec03/product")
                .bodyValue(product)
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void postBody(){
        var mono = Mono.fromSupplier(() -> new Product(null,"demo product",200))
                .delayElement(Duration.ofSeconds(1));
        var product = new Product(null,"demo product",200);
        this.client.post()
                .uri("/lec03/product")
                .body(mono, Product.class)
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
