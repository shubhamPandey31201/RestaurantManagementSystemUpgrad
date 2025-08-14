package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateOrderRequest;
import com.upgrad.mini_project_two.dtos.UpdateOrderStatusRequest;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.enums.OrderStatus;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.repositories.OrderRepository;
import com.upgrad.mini_project_two.repositories.TableBookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final TableBookingRepository tableBookingRepository;

    public Order createOrder(CreateOrderRequest request) {
        log.info("Creating order for table booking ID: {}", request.getTableBookingId());

        TableBooking tableBooking = tableBookingRepository.findById(request.getTableBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TableBooking not found with id: " + request.getTableBookingId()
                ));


        Order order = Order.builder()
                .tableNumber(tableBooking.getTableNumber())
                .items(request.getItems())
                .status(OrderStatus.PLACED)
                .tableBooking(tableBooking)
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with id: {}", savedOrder.getId());
        return savedOrder;
    }


    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(UUID id) {
        log.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order updateOrderStatus(UUID id, UpdateOrderStatusRequest request) {
        log.info("Updating order status for id: {} to {}", id, request.getStatus());

        Order order = getOrderById(id);
        validateStatusTransition(order.getStatus(), request.getStatus());

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);

        log.info("Order status updated successfully for id: {}", updatedOrder.getId());
        return updatedOrder;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByTableNumber(Integer tableNumber) {
        log.info("Fetching orders for table: {}", tableNumber);
        return orderRepository.findByTableNumber(tableNumber);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.SERVED && newStatus != OrderStatus.SERVED) {
            throw new ValidationException("Cannot change status of a served order");
        }

        if (currentStatus == OrderStatus.PLACED && newStatus == OrderStatus.SERVED) {
            throw new ValidationException("Order must go through IN_KITCHEN status before being served");
        }
    }
}