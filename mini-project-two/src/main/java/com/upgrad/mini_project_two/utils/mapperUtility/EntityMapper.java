package com.upgrad.mini_project_two.utils.mapperUtility;

import com.upgrad.mini_project_two.dtos.OrderResponse;
import com.upgrad.mini_project_two.dtos.TableBookingResponse;
import com.upgrad.mini_project_two.dtos.UserResponse;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper implements IEntityMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public List<UserResponse> toUserResponseList(List<User> users) {
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    public TableBookingResponse toTableBookingResponse(TableBooking booking) {
        return TableBookingResponse.builder()
                .id(booking.getId())
                .customerName(booking.getCustomerName())
                .bookingTime(booking.getBookingTime())
                .tableNumber(booking.getTableNumber())
                .numberOfGuests(booking.getNumberOfGuests())
                .createdAt(booking.getCreatedAt())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .build();
    }

    public List<TableBookingResponse> toTableBookingResponseList(List<TableBooking> bookings) {
        return bookings.stream()
                .map(this::toTableBookingResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .tableNumber(order.getTableNumber())
                .items(order.getItems())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .tableBookingId(order.getTableBooking()!= null ? order.getTableBooking().getId() : null)
                .build();
    }

    public List<OrderResponse> toOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }
}