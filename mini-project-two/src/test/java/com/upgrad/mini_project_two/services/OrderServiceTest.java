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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TableBookingRepository tableBookingRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_SuccessfulCreation_ReturnsSavedOrder() {
        UUID bookingId = UUID.randomUUID();
        TableBooking booking = TableBooking.builder().id(bookingId).tableNumber(5).build();
        CreateOrderRequest request = new CreateOrderRequest("Pizza", bookingId);

        when(tableBookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        Order savedOrder = Order.builder().id(UUID.randomUUID()).tableNumber(5).items("Pizza").status(OrderStatus.PLACED).tableBooking(booking).build();
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(request);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).isEqualTo("Pizza");
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PLACED);
        assertThat(result.getTableBooking()).isEqualTo(booking);
    }

    @Test
    void createOrder_TableBookingNotFound_ThrowsResourceNotFoundException() {
        UUID bookingId = UUID.randomUUID();
        CreateOrderRequest request = new CreateOrderRequest("Pizza", bookingId);

        when(tableBookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("TableBooking not found with id");
    }

    @Test
    void getOrderById_OrderExists_ReturnsOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().id(orderId).build();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(orderId);

        assertThat(result).isEqualTo(order);
    }

    @Test
    void getOrderById_OrderNotFound_ThrowsResourceNotFoundException() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found with id");
    }

    @Test
    void updateOrderStatus_ValidTransition_UpdatesStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().id(orderId).status(OrderStatus.PLACED).build();
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest(OrderStatus.IN_KITCHEN);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order updated = orderService.updateOrderStatus(orderId, request);

        assertThat(updated.getStatus()).isEqualTo(OrderStatus.IN_KITCHEN);
    }

    @Test
    void updateOrderStatus_ServedToOther_ThrowsValidationException() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().id(orderId).status(OrderStatus.SERVED).build();
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest(OrderStatus.PLACED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.updateOrderStatus(orderId, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Cannot change status of a served order");
    }

    @Test
    void updateOrderStatus_PlacedToServed_ThrowsValidationException() {
        UUID orderId = UUID.randomUUID();
        Order order = Order.builder().id(orderId).status(OrderStatus.PLACED).build();
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest(OrderStatus.SERVED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.updateOrderStatus(orderId, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Order must go through IN_KITCHEN status before being served");
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        List<Order> orders = Arrays.asList(Order.builder().id(UUID.randomUUID()).build());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertThat(result).isEqualTo(orders);
    }

    @Test
    void getOrdersByTableNumber_ReturnsOrdersForTable() {
        int tableNumber = 10;
        List<Order> orders = Arrays.asList(Order.builder().tableNumber(tableNumber).build());
        when(orderRepository.findByTableNumber(tableNumber)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByTableNumber(tableNumber);

        assertThat(result).isEqualTo(orders);
    }

    @Test
    void getOrdersByStatus_ReturnsOrdersWithStatus() {
        OrderStatus status = OrderStatus.IN_KITCHEN;
        List<Order> orders = Arrays.asList(Order.builder().status(status).build());
        when(orderRepository.findByStatus(status)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByStatus(status);

        assertThat(result).isEqualTo(orders);
    }
}