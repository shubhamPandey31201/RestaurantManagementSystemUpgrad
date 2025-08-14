package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateBookingRequest;
import com.upgrad.mini_project_two.dtos.UpdateBookingRequest;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.repositories.TableBookingRepository;
import com.upgrad.mini_project_two.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TableBookingService implements ITableBookingService{

    private final TableBookingRepository tableBookingRepository;
    private final UserRepository userRepository;

    public TableBooking createBooking(CreateBookingRequest request) {
        log.info("Creating booking for table: {} at {}", request.getTableNumber(), request.getBookingTime());

        validateBookingAvailability(request.getTableNumber(), request.getBookingTime());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        TableBooking booking = TableBooking.builder()
                .customerName(request.getCustomerName())
                .bookingTime(request.getBookingTime())
                .tableNumber(request.getTableNumber())
                .numberOfGuests(request.getNumberOfGuests())
                .user(user)
                .build();

        TableBooking savedBooking = tableBookingRepository.save(booking);
        log.info("Booking created successfully with id: {}", savedBooking.getId());
        return savedBooking;
    }

    @Transactional(readOnly = true)
    public List<TableBooking> getAllBookings() {
        log.info("Fetching all bookings");
        return tableBookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TableBooking getBookingById(UUID id) {
        log.info("Fetching booking with id: {}", id);
        return tableBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    public TableBooking updateBooking(UUID id, UpdateBookingRequest request) {
        log.info("Updating booking with id: {}", id);

        TableBooking booking = getBookingById(id);

        if (request.getCustomerName() != null) {
            booking.setCustomerName(request.getCustomerName());
        }
        if (request.getBookingTime() != null) {
            validateBookingAvailability(booking.getTableNumber(), request.getBookingTime(), id);
            booking.setBookingTime(request.getBookingTime());
        }
        if (request.getTableNumber() != null) {
            validateBookingAvailability(request.getTableNumber(), booking.getBookingTime(), id);
            booking.setTableNumber(request.getTableNumber());
        }
        if (request.getNumberOfGuests() != null) {
            booking.setNumberOfGuests(request.getNumberOfGuests());
        }
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
            booking.setUser(user);
        }

        TableBooking updatedBooking = tableBookingRepository.save(booking);
        log.info("Booking updated successfully with id: {}", updatedBooking.getId());
        return updatedBooking;
    }

    public void deleteBooking(UUID id) {
        log.info("Deleting booking with id: {}", id);

        if (!tableBookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }

        tableBookingRepository.deleteById(id);
        log.info("Booking deleted successfully with id: {}", id);
    }

    private void validateBookingAvailability(Integer tableNumber, LocalDateTime bookingTime) {
        validateBookingAvailability(tableNumber, bookingTime, null);
    }

    private void validateBookingAvailability(Integer tableNumber, LocalDateTime bookingTime, UUID excludeBookingId) {
        LocalDateTime startTime = bookingTime.minusHours(2);
        LocalDateTime endTime = bookingTime.plusHours(2);

        List<TableBooking> conflictingBookings = tableBookingRepository.findConflictingBookings(
                tableNumber, startTime, endTime);

        if (excludeBookingId != null) {
            conflictingBookings = conflictingBookings.stream()
                    .filter(booking -> !booking.getId().equals(excludeBookingId))
                    .toList();
        }

        if (!conflictingBookings.isEmpty()) {
            throw new ValidationException("Table " + tableNumber + " is not available at " + bookingTime);
        }
    }
}