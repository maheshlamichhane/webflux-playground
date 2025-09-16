package com.vinsguru.webflux_playground.sec06.config;

import com.vinsguru.webflux_playground.sec06.exception.ApplicationException;
import com.vinsguru.webflux_playground.sec06.validator.RequestValidator;
import com.vinsguru.webflux_playground.sec06.service.CustomerService;
import com.vinsguru.webflux_playground.sec06.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerRequestHandler {

    @Autowired
    private CustomerService customerService;

    public Mono<ServerResponse> allCustomers(ServerRequest request){
        return this.customerService.getAllCustomers()
                .as(flux -> ServerResponse.ok().body(flux, CustomerDTO.class));
    }

    public Mono<ServerResponse> paginatedCustomer(ServerRequest request){
        var page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        var size = request.queryParam("size").map(Integer::parseInt).orElse(3);
        return this.customerService.getAllCustomers(page,size)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        return request.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(this.customerService::saveCustomer)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(validateReq -> this.customerService.updateCustomer(id,validateReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request){
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then(ServerResponse.ok().build());
    }
}
