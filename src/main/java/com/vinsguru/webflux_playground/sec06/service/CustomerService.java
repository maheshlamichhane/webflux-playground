package com.vinsguru.webflux_playground.sec06.service;

import com.vinsguru.webflux_playground.sec06.dto.CustomerDTO;
import com.vinsguru.webflux_playground.sec06.mapper.EntityDtoMapper;
import com.vinsguru.webflux_playground.sec06.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Flux<CustomerDTO> getAllCustomers(){
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDTO);

    }

    public Flux<CustomerDTO> getAllCustomers(Integer page, Integer size){
        return this.customerRepository.findBy(PageRequest.of(page-1,size))
                .map(EntityDtoMapper::toDTO);

    }

    public Mono<CustomerDTO> getCustomerById(Integer id){
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDTO);
    }

    public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> mono){
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(entity -> this.customerRepository.save(entity))
                .map(EntityDtoMapper::toDTO);
    }

    public Mono<CustomerDTO> updateCustomer(Integer id, Mono<CustomerDTO> mono){
        return this.customerRepository.findById(id)
                .flatMap(entity -> mono)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDTO);
    }


    public Mono<Boolean> deleteCustomerById(Integer id){
        return this.customerRepository.deleteCustomerById(id);
    }

}
