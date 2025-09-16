package com.vinsguru.webflux_playground.sec06.config;

import com.vinsguru.webflux_playground.sec06.exception.CustomerNotFoundException;
import com.vinsguru.webflux_playground.sec06.advice.ApplicationExceptionHandler;
import com.vinsguru.webflux_playground.sec06.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Autowired
    private ApplicationExceptionHandler applicationExceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> route1(){
       return  RouterFunctions.route()
                .path("customer",this::route2)
                .POST("/customers",this.customerRequestHandler::saveCustomer)
                .PUT("/customers/{id}",this.customerRequestHandler::updateCustomer)
                .DELETE("/customers/{id}",this.customerRequestHandler::deleteCustomer)
                .onError(CustomerNotFoundException.class,this.applicationExceptionHandler::handleException)
                .onError(InvalidInputException.class,this.applicationExceptionHandler::handleException)
                .filter(((request, next) -> {
                    //write authentication filter logic
                    return next.handle(request) ;
                }))
               .filter(((request, next) -> {
                   //write authorizatoin filter logic
                   return next.handle(request) ;
               }))
                .build();
    }


    private RouterFunction<ServerResponse> route2(){
        return  RouterFunctions.route()
//                .GET(req -> true,this.customerRequestHandler::allCustomers)
                .GET("/paginated",this.customerRequestHandler::paginatedCustomer)
                .GET("/{id}",this.customerRequestHandler::getCustomer)
                .GET(this.customerRequestHandler::allCustomers)
                .onError(CustomerNotFoundException.class,this.applicationExceptionHandler::handleException)
                .onError(InvalidInputException.class,this.applicationExceptionHandler::handleException)
                .build();
    }
}
