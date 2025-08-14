package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateBookingRequest;
import com.upgrad.mini_project_two.dtos.UpdateBookingRequest;
import com.upgrad.mini_project_two.entities.TableBooking;

import java.util.List;
import java.util.UUID;

public interface ITableBookingService {
    TableBooking createBooking(CreateBookingRequest request);
    List<TableBooking> getAllBookings();
    TableBooking getBookingById(UUID id);
    TableBooking updateBooking(UUID id, UpdateBookingRequest request);
    void deleteBooking(UUID id);
}