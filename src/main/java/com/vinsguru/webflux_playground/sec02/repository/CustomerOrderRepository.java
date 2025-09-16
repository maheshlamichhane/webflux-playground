package com.vinsguru.webflux_playground.sec02.repository;

import com.vinsguru.webflux_playground.sec02.dto.OrderDetails;
import com.vinsguru.webflux_playground.sec02.entity.CustomerOrder;
import com.vinsguru.webflux_playground.sec02.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {

    @Query("""
            SELECT p.*
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p On co.product_id = p.id
            WHERE
                c.name = :name
            """)
    Flux<Product> getProductsOrdersByCustomer(String name);


    @Query("""
            SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
            WHERE
                p.description = :description
            ORDER By co.amount DESC
            """)
    Flux<OrderDetails> getOrderDetailsByProduct(String description);

}
