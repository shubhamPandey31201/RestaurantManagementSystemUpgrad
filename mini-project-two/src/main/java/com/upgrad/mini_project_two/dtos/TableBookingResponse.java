package com.upgrad.mini_project_two.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableBookingResponse {
    private UUID id;
    private String customerName;
    private LocalDateTime bookingTime;
    private Integer tableNumber;
    private Integer numberOfGuests;
    private LocalDateTime createdAt;
    private UUID userId;
}