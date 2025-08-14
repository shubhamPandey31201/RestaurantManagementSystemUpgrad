package com.upgrad.mini_project_two.repositories;

import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByTableNumber(Integer tableNumber);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByTableNumberAndStatus(Integer tableNumber, OrderStatus status);
    List<Order> findByTableBookingId(UUID tableBookingId); // Optional
}