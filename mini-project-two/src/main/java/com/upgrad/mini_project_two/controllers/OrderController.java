
package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.dtos.CreateOrderRequest;
import com.upgrad.mini_project_two.dtos.UpdateOrderStatusRequest;
import com.upgrad.mini_project_two.dtos.OrderResponse;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.services.IOrderService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final IOrderService orderService;
    private final IEntityMapper entityMapper;

    @Autowired
    public OrderController(IOrderService orderService, IEntityMapper entityMapper) {
        this.orderService = orderService;
        this.entityMapper = entityMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        logger.info("Entered createOrder with request: {}", request);
        Order order = orderService.createOrder(request);
        OrderResponse response = entityMapper.toOrderResponse(order);
        logger.info("Order created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        logger.info("Entered getAllOrders");
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> response = entityMapper.toOrderResponseList(orders);
        logger.info("Fetched all orders successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {
        logger.info("Entered getOrderById with id: {}", id);
        Order order = orderService.getOrderById(id);
        OrderResponse response = entityMapper.toOrderResponse(order);
        logger.info("Fetched order successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        logger.info("Entered updateOrder with id: {} and request: {}", id, request);
        Order order = orderService.updateOrderStatus(id, request);
        OrderResponse response = entityMapper.toOrderResponse(order);
        logger.info("Order updated successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}