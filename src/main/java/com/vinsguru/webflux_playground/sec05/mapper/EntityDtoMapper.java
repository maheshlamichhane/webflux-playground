package com.vinsguru.webflux_playground.sec05.mapper;

import com.vinsguru.webflux_playground.sec05.dto.CustomerDTO;
import com.vinsguru.webflux_playground.sec05.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDTO customerDTO){
        var customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setId(customerDTO.id());
        return customer;
    }

    public static CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getEmail());
    }
}
