package com.upgrad.mini_project_two.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequest {
    private String customerName;

    @Future(message = "Booking time must be in the future")
    private LocalDateTime bookingTime;

    @Min(value = 1, message = "Table number must be positive")
    private Integer tableNumber;

    @Min(value = 1, message = "Number of guests must be at least 1")
    private Integer numberOfGuests;

    private UUID userId;
}