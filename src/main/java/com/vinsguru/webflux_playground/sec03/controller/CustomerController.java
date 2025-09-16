package com.vinsguru.webflux_playground.sec03.controller;

import com.vinsguru.webflux_playground.sec03.dto.CustomerDTO;
import com.vinsguru.webflux_playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<CustomerDTO>> getCustomer(@PathVariable Integer id){
        return this.customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> mono){
        return this.customerService.saveCustomer(mono);
    }


    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDTO>> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDTO> mono){
        return this.customerService.updateCustomer(id,mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id){
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .map(b -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
