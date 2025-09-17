package com.vinsguru.webflux_playground.sec07;

import com.vinsguru.webflux_playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

public class Lec08ExchangeTest extends AbstractWebClient{

    private static final Logger log = LoggerFactory.getLogger(Lec07BasicAuthTest.class);
    private WebClient client = createWebClient(b -> b.filter(tokenGenerator()));

    @Test
    public void exchangeFilter(){
        this.client.get()
                .uri("/lec09/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private ExchangeFilterFunction tokenGenerator(){
        return (request, next) -> {
            var token = UUID.randomUUID().toString().replace("-","");
            log.info("generated token: {}",token);
            var modifiedRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth(token)).build();
            return next.exchange(modifiedRequest);
        };
    }
}
