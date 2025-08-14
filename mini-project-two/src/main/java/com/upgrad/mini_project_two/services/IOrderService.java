package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateOrderRequest;
import com.upgrad.mini_project_two.dtos.UpdateOrderStatusRequest;
import com.upgrad.mini_project_two.dtos.UserLoginDTO;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    Order createOrder(CreateOrderRequest request);
    List<Order> getAllOrders();
    Order getOrderById(UUID id);
    Order updateOrderStatus(UUID id, UpdateOrderStatusRequest request);
    List<Order> getOrdersByTableNumber(Integer tableNumber);
    List<Order> getOrdersByStatus(OrderStatus status);
}