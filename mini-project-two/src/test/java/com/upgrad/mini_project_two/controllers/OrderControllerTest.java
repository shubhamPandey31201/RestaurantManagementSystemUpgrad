package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.dtos.CreateOrderRequest;
import com.upgrad.mini_project_two.dtos.OrderResponse;
import com.upgrad.mini_project_two.dtos.UpdateOrderStatusRequest;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.services.IOrderService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Mock
    private IOrderService orderService;
    @Mock
    private IEntityMapper entityMapper;
    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ValidRequest_ReturnsCreatedOrderResponse() {
        CreateOrderRequest request = new CreateOrderRequest();
        Order order = new Order();
        OrderResponse response = new OrderResponse();

        when(orderService.createOrder(request)).thenReturn(order);
        when(entityMapper.toOrderResponse(order)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.createOrder(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getAllOrders_ReturnsListOfOrderResponses() {
        List<Order> orders = List.of(new Order(), new Order());
        List<OrderResponse> responses = List.of(new OrderResponse(), new OrderResponse());

        when(orderService.getAllOrders()).thenReturn(orders);
        when(entityMapper.toOrderResponseList(orders)).thenReturn(responses);

        ResponseEntity<List<OrderResponse>> result = orderController.getAllOrders();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(responses);
    }

    @Test
    void getOrderById_ValidId_ReturnsOrderResponse() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        OrderResponse response = new OrderResponse();

        when(orderService.getOrderById(id)).thenReturn(order);
        when(entityMapper.toOrderResponse(order)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.getOrderById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateOrder_ValidRequest_ReturnsUpdatedOrderResponse() {
        UUID id = UUID.randomUUID();
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        Order order = new Order();
        OrderResponse response = new OrderResponse();

        when(orderService.updateOrderStatus(id, request)).thenReturn(order);
        when(entityMapper.toOrderResponse(order)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.updateOrder(id, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getOrderById_InvalidId_ThrowsException() {
        UUID invalidId = UUID.randomUUID();
        when(orderService.getOrderById(invalidId)).thenThrow(new NoSuchElementException("Order not found"));

        assertThatThrownBy(() -> orderController.getOrderById(invalidId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Order not found");
    }

    @Test
    void updateOrder_InvalidId_ThrowsException() {
        UUID invalidId = UUID.randomUUID();
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        when(orderService.updateOrderStatus(invalidId, request)).thenThrow(new NoSuchElementException("Order not found"));

        assertThatThrownBy(() -> orderController.updateOrder(invalidId, request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Order not found");
    }
}
