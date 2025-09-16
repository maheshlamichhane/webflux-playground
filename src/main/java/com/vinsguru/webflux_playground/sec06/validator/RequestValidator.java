package com.vinsguru.webflux_playground.sec06.validator;

import com.vinsguru.webflux_playground.sec06.dto.CustomerDTO;
import com.vinsguru.webflux_playground.sec06.exception.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDTO>> validate() {
        return mono -> mono
                .filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingValidEmail());
    }



    private static Predicate<CustomerDTO> hasName(){
        return dto -> Objects.nonNull(dto.name());
    }

    private static Predicate<CustomerDTO>  hasValidEmail(){
        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
    }


}
