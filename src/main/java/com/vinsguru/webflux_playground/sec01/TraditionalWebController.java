package com.vinsguru.webflux_playground.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("traditional")
public class TraditionalWebController {

    private static Logger log = LoggerFactory.getLogger(TraditionalWebController.class);
    private final RestClient restClient = RestClient.builder()
            .requestFactory(new JdkClientHttpRequestFactory())
            .baseUrl("http://localhost:7070")
            .build();


    @GetMapping("/products")
    public List<Product> getProducts(){
        var list = this.restClient.get()
                .uri("/demo01/products/notorious")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>(){});
        log.info("received response: {}",list);
        return list;
    }


}
