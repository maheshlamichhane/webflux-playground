package com.vinsguru.webflux_playground.sec07;

import com.vinsguru.webflux_playground.sec07.dto.CalculatorResponse;
import com.vinsguru.webflux_playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec06QueryParamTest extends AbstractWebClient{

    private static final Logger log = LoggerFactory.getLogger(Lec04HeaderTest.class);
    private WebClient client = createWebClient(b -> b.defaultHeader("caller-id","order-service"));

    @Test
    public void uriBuilder(){
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";
        this.client.get()
                .uri(builder -> builder.path(path).query(query).build(10,20,"+"))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
