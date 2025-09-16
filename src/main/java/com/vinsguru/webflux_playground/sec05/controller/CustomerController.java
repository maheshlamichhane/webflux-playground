package com.vinsguru.webflux_playground.sec05.controller;

import com.vinsguru.webflux_playground.sec05.dto.CustomerDTO;
import com.vinsguru.webflux_playground.sec05.exception.ApplicationException;
import com.vinsguru.webflux_playground.sec05.service.CustomerService;
import com.vinsguru.webflux_playground.sec05.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDTO> allCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDTO>> allCustomers(@RequestParam(defaultValue ="1") Integer page, @RequestParam(defaultValue = "3") Integer size){
        return customerService.getAllCustomers(page,size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDTO> getCustomer(@PathVariable Integer id){
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> mono){
        var validatedMono = mono.transform(RequestValidator.validate());
        return this.customerService.saveCustomer(validatedMono);
    }


    @PutMapping("{id}")
    public Mono<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDTO> mono){
        return mono.transform(RequestValidator.validate())
                .as(validReq -> this.customerService.updateCustomer(id,validReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();
    }


}
