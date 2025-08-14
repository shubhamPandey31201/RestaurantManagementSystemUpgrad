package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.dtos.CreateBookingRequest;
import com.upgrad.mini_project_two.dtos.UpdateBookingRequest;
import com.upgrad.mini_project_two.dtos.TableBookingResponse;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.services.ITableBookingService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/table-bookings")
public class TableBookingController {

    private static final Logger logger = LoggerFactory.getLogger(TableBookingController.class);

    private final ITableBookingService tableBookingService;
    private final IEntityMapper entityMapper;

    @Autowired
    TableBookingController(ITableBookingService tableBookingService, IEntityMapper entityMapper) {
        this.tableBookingService = tableBookingService;
        this.entityMapper = entityMapper;
    }

    @PostMapping
    public ResponseEntity<TableBookingResponse> createTableBooking(@Valid @RequestBody CreateBookingRequest request) {
        logger.info("Entered createTableBooking with request: {}", request);
        TableBooking tableBooking = tableBookingService.createBooking(request);
        TableBookingResponse response = entityMapper.toTableBookingResponse(tableBooking);
        logger.info("Table booking created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TableBookingResponse>> getAllTableBookings() {
        logger.info("Entered getAllTableBookings");
        List<TableBooking> tableBookings = tableBookingService.getAllBookings();
        List<TableBookingResponse> response = entityMapper.toTableBookingResponseList(tableBookings);
        logger.info("Fetched all table bookings successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableBookingResponse> getTableBookingById(@PathVariable UUID id) {
        logger.info("Entered getTableBookingById with id: {}", id);
        TableBooking tableBooking = tableBookingService.getBookingById(id);
        TableBookingResponse response = entityMapper.toTableBookingResponse(tableBooking);
        logger.info("Fetched table booking successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableBookingResponse> updateTableBooking(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookingRequest request) {
        logger.info("Entered updateTableBooking with id: {} and request: {}", id, request);
        TableBooking tableBooking = tableBookingService.updateBooking(id, request);
        TableBookingResponse response = entityMapper.toTableBookingResponse(tableBooking);
        logger.info("Table booking updated successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableBooking(@PathVariable UUID id) {
        logger.info("Entered deleteTableBooking with id: {}", id);
        tableBookingService.deleteBooking(id);
        logger.info("Table booking deleted successfully for id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}