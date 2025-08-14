package com.upgrad.mini_project_two.utils.mapperUtility;

import com.upgrad.mini_project_two.dtos.OrderResponse;
import com.upgrad.mini_project_two.dtos.TableBookingResponse;
import com.upgrad.mini_project_two.dtos.UserResponse;
import com.upgrad.mini_project_two.entities.Order;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.entities.User;

import java.util.List;

public interface IEntityMapper {
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> users);

    TableBookingResponse toTableBookingResponse(TableBooking booking);
    List<TableBookingResponse> toTableBookingResponseList(List<TableBooking> bookings);

    OrderResponse toOrderResponse(Order order);
    List<OrderResponse> toOrderResponseList(List<Order> orders);
}