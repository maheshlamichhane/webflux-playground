package com.vinsguru.webflux_playground.sec07;

import com.vinsguru.webflux_playground.sec07.dto.CalculatorResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class Lec05ErrorHandlingTest extends AbstractWebClient {


    private static final Logger log = LoggerFactory.getLogger(Lec04HeaderTest.class);
    private WebClient client = createWebClient(b -> b.defaultHeader("caller-id","order-service"));

    @Test
    public void errorHandlingTest(){
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}",1,2)
                .header("operation","@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnError(WebClientResponseException.class,ex -> log.info("{}",ex.getResponseBodyAs(ProblemDetail.class)))
                .onErrorReturn(WebClientResponseException.BadRequest.class,new CalculatorResponse(0,0,null,0.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void exchange(){
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}",1,2)
                .header("operation","@")
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private Mono<CalculatorResponse> decode(ClientResponse response){
        log.info("status code: {}",response.statusCode());
        if(response.statusCode().isError()){
            return response.bodyToMono(CalculatorResponse.class)
                    .doOnNext(pd -> log.info("{}",pd))
                    .then(Mono.empty());
        }
        return response.bodyToMono(CalculatorResponse.class);

    }
}
