package com.vinsguru.webflux_playground.sec08.controller;

import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import com.vinsguru.webflux_playground.sec08.dto.UploadResponse;
import com.vinsguru.webflux_playground.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping(value="upload",consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux){
        log.info("invoked");
        return this.productService.saveProducts(flux.doOnNext(dto -> log.info("received:{}",dto)))
                .then(this.productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(),count));
    }

    @GetMapping(value="download",produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> downloadProducts(){
        return this.productService.allProducts();
    }

}
