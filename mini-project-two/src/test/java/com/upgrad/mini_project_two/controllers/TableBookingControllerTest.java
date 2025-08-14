package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.dtos.CreateBookingRequest;
import com.upgrad.mini_project_two.dtos.UpdateBookingRequest;
import com.upgrad.mini_project_two.dtos.TableBookingResponse;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.services.ITableBookingService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableBookingControllerTest {

    @Mock
    private ITableBookingService tableBookingService;
    @Mock
    private IEntityMapper entityMapper;

    @InjectMocks
    private TableBookingController tableBookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTableBooking_ReturnsCreatedResponse() {
        CreateBookingRequest request = new CreateBookingRequest("Alice", LocalDateTime.now().plusDays(1), 1, 2, UUID.randomUUID());
        TableBooking booking = TableBooking.builder().id(UUID.randomUUID()).customerName("Alice").build();
        TableBookingResponse response = TableBookingResponse.builder()
                .id(booking.getId())
                .customerName("Alice")
                .bookingTime(booking.getBookingTime())
                .tableNumber(booking.getTableNumber())
                .numberOfGuests(booking.getNumberOfGuests())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .build();

        when(tableBookingService.createBooking(request)).thenReturn(booking);
        when(entityMapper.toTableBookingResponse(booking)).thenReturn(response);

        ResponseEntity<TableBookingResponse> result = tableBookingController.createTableBooking(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getAllTableBookings_ReturnsListOfResponses() {
        TableBooking booking = TableBooking.builder().id(UUID.randomUUID()).customerName("Bob").build();
        List<TableBooking> bookings = List.of(booking);
        List<TableBookingResponse> responses = List.of(
                TableBookingResponse.builder()
                        .id(booking.getId())
                        .customerName("Bob")
                        .bookingTime(booking.getBookingTime())
                        .tableNumber(booking.getTableNumber())
                        .numberOfGuests(booking.getNumberOfGuests())
                        .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                        .build()
        );

        when(tableBookingService.getAllBookings()).thenReturn(bookings);
        when(entityMapper.toTableBookingResponseList(bookings)).thenReturn(responses);

        ResponseEntity<List<TableBookingResponse>> result = tableBookingController.getAllTableBookings();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(responses);
    }

    @Test
    void getTableBookingById_ReturnsResponse() {
        UUID id = UUID.randomUUID();
        TableBooking booking = TableBooking.builder().id(id).customerName("Jane").build();
        TableBookingResponse response = TableBookingResponse.builder()
                .id(id)
                .customerName("Jane")
                .bookingTime(booking.getBookingTime())
                .tableNumber(booking.getTableNumber())
                .numberOfGuests(booking.getNumberOfGuests())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .build();

        when(tableBookingService.getBookingById(id)).thenReturn(booking);
        when(entityMapper.toTableBookingResponse(booking)).thenReturn(response);

        ResponseEntity<TableBookingResponse> result = tableBookingController.getTableBookingById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateTableBooking_ReturnsUpdatedResponse() {
        UUID id = UUID.randomUUID();
        UpdateBookingRequest request = new UpdateBookingRequest("Updated", LocalDateTime.now().plusDays(2), 2, 4, UUID.randomUUID());
        TableBooking booking = TableBooking.builder().id(id).customerName("Updated").build();
        TableBookingResponse response = TableBookingResponse.builder()
                .id(id)
                .customerName("Updated")
                .bookingTime(booking.getBookingTime())
                .tableNumber(booking.getTableNumber())
                .numberOfGuests(booking.getNumberOfGuests())
                .userId(booking.getUser() != null ? booking.getUser().getId() : null)
                .build();

        when(tableBookingService.updateBooking(id, request)).thenReturn(booking);
        when(entityMapper.toTableBookingResponse(booking)).thenReturn(response);

        ResponseEntity<TableBookingResponse> result = tableBookingController.updateTableBooking(id, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void deleteTableBooking_ReturnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> result = tableBookingController.deleteTableBooking(id);

        verify(tableBookingService).deleteBooking(id);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();
    }

    @Test
    void createTableBooking_ValidationException_Throws() {
        CreateBookingRequest request = new CreateBookingRequest("Alice", LocalDateTime.now().plusDays(1), 1, 2, UUID.randomUUID());
        when(tableBookingService.createBooking(request)).thenThrow(new ValidationException("Table not available"));

        assertThatThrownBy(() -> tableBookingController.createTableBooking(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Table not available");
    }

    @Test
    void getTableBookingById_NotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(tableBookingService.getBookingById(id)).thenThrow(new ResourceNotFoundException("Booking not found"));

        assertThatThrownBy(() -> tableBookingController.getTableBookingById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found");
    }

    @Test
    void updateTableBooking_ValidationException_Throws() {
        UUID id = UUID.randomUUID();
        UpdateBookingRequest request = new UpdateBookingRequest("Updated", LocalDateTime.now().plusDays(2), 2, 4, UUID.randomUUID());
        when(tableBookingService.updateBooking(id, request)).thenThrow(new ValidationException("Invalid update"));

        assertThatThrownBy(() -> tableBookingController.updateTableBooking(id, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid update");
    }

    @Test
    void deleteTableBooking_NotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Booking not found")).when(tableBookingService).deleteBooking(id);

        assertThatThrownBy(() -> tableBookingController.deleteTableBooking(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found");
    }
}
