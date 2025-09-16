package com.vinsguru.webflux_playground.sec05;

import com.vinsguru.webflux_playground.sec05.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
public class CustomerserviceTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void unauthorized(){

        this.webTestClient.get()
                .uri("/customer")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        //invalid token
        this.validateGet("secret",HttpStatus.UNAUTHORIZED);

    }

    @Test
    public void standardCategory(){
        this.validateGet("secret123",HttpStatus.OK);
        this.validatePost("secret123",HttpStatus.FORBIDDEN);
    }

    private void validateGet(String token, HttpStatus expectedStatus){
        this.webTestClient.get()
                .uri("/customers")
                .header("auth-token",token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private void validatePost(String token, HttpStatus expectedStatus){
        var dto = new CustomerDTO(null,"marshal","marshal@gmail.com");
        this.webTestClient.post()
                .uri("/customers")
                .bodyValue(dto)
                .header("auth-token",token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}
