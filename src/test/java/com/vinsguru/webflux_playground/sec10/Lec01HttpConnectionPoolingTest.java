package com.vinsguru.webflux_playground.sec10;

import com.vinsguru.webflux_playground.sec10.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

public class Lec01HttpConnectionPoolingTest extends AbstractWebClient{

    private final WebClient client = createWebClient(b -> {
        var poolSize = 500;
        var provider = ConnectionProvider.builder("vins")
                .lifo()
                .maxConnections(poolSize)
                .pendingAcquireMaxCount(poolSize * 5)
                .build();
        var httpCient = HttpClient.create(provider)
                .compress(true)
                .keepAlive(true);
        b.clientConnector(new ReactorClientHttpConnector(httpCient));
    });

    @Test
    public void concurrentRequest(){
        var max = 501;
        Flux.range(1,max)
                .flatMap(this::getProduct,max)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(l -> Assertions.assertEquals(max,l.size()))
                .expectComplete()
                .verify();
    }



    private Mono<Product> getProduct(int id){
        return this.client.get()
                .uri("/product/{id}",id)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
