package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateBookingRequest;
import com.upgrad.mini_project_two.dtos.UpdateBookingRequest;
import com.upgrad.mini_project_two.entities.TableBooking;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.repositories.TableBookingRepository;
import com.upgrad.mini_project_two.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableBookingServiceTest {

    @Mock
    private TableBookingRepository tableBookingRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TableBookingService tableBookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_SuccessfulCreation_ReturnsSavedBooking() {
        UUID userId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now().plusDays(1);
        CreateBookingRequest request = new CreateBookingRequest("Alice", bookingTime, 5, 2, userId);

        when(tableBookingRepository.findConflictingBookings(eq(5), any(), any())).thenReturn(Collections.emptyList());
        User user = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        TableBooking savedBooking = TableBooking.builder().id(UUID.randomUUID()).customerName("Alice").bookingTime(bookingTime).tableNumber(5).numberOfGuests(2).user(user).build();
        when(tableBookingRepository.save(any(TableBooking.class))).thenReturn(savedBooking);

        TableBooking result = tableBookingService.createBooking(request);

        assertThat(result.getCustomerName()).isEqualTo("Alice");
        assertThat(result.getTableNumber()).isEqualTo(5);
        assertThat(result.getBookingTime()).isEqualTo(bookingTime);
        assertThat(result.getUser()).isEqualTo(user);
    }

    @Test
    void createBooking_UserNotFound_ThrowsResourceNotFoundException() {
        UUID userId = UUID.randomUUID();
        CreateBookingRequest request = new CreateBookingRequest("Alice", LocalDateTime.now().plusDays(1), 5, 2, userId);

        when(tableBookingRepository.findConflictingBookings(eq(5), any(), any())).thenReturn(Collections.emptyList());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tableBookingService.createBooking(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id");
    }

    @Test
    void createBooking_ConflictingBooking_ThrowsValidationException() {
        UUID userId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now().plusDays(1);
        CreateBookingRequest request = new CreateBookingRequest("Alice", bookingTime, 5, 2, userId);

        when(tableBookingRepository.findConflictingBookings(eq(5), any(), any()))
                .thenReturn(List.of(TableBooking.builder().id(UUID.randomUUID()).build()));

        assertThatThrownBy(() -> tableBookingService.createBooking(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("not available");
    }

    @Test
    void getAllBookings_ReturnsListOfBookings() {
        List<TableBooking> bookings = Arrays.asList(TableBooking.builder().id(UUID.randomUUID()).build());
        when(tableBookingRepository.findAll()).thenReturn(bookings);

        List<TableBooking> result = tableBookingService.getAllBookings();

        assertThat(result).isEqualTo(bookings);
    }

    @Test
    void getBookingById_BookingExists_ReturnsBooking() {
        UUID id = UUID.randomUUID();
        TableBooking booking = TableBooking.builder().id(id).build();
        when(tableBookingRepository.findById(id)).thenReturn(Optional.of(booking));

        TableBooking result = tableBookingService.getBookingById(id);

        assertThat(result).isEqualTo(booking);
    }

    @Test
    void getBookingById_BookingNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(tableBookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tableBookingService.getBookingById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found with id");
    }

    @Test
    void updateBooking_UpdatesAllFieldsSuccessfully() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now().plusDays(2);
        TableBooking booking = TableBooking.builder().id(id).customerName("Old").bookingTime(LocalDateTime.now()).tableNumber(1).numberOfGuests(2).user(User.builder().id(UUID.randomUUID()).build()).build();
        UpdateBookingRequest request = new UpdateBookingRequest("New", bookingTime, 3, 4, userId);

        when(tableBookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(tableBookingRepository.findConflictingBookings(eq(3), any(), any())).thenReturn(Collections.emptyList());
        User user = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tableBookingRepository.save(any(TableBooking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TableBooking updated = tableBookingService.updateBooking(id, request);

        assertThat(updated.getCustomerName()).isEqualTo("New");
        assertThat(updated.getBookingTime()).isEqualTo(bookingTime);
        assertThat(updated.getTableNumber()).isEqualTo(3);
        assertThat(updated.getNumberOfGuests()).isEqualTo(4);
        assertThat(updated.getUser()).isEqualTo(user);
    }

    @Test
    void updateBooking_BookingNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        UpdateBookingRequest request = new UpdateBookingRequest("New", null, null, null, null);

        when(tableBookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tableBookingService.updateBooking(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found with id");
    }

    @Test
    void updateBooking_ConflictingBooking_ThrowsValidationException() {
        UUID id = UUID.randomUUID();
        TableBooking booking = TableBooking.builder().id(id).customerName("Old").bookingTime(LocalDateTime.now()).tableNumber(1).numberOfGuests(2).user(User.builder().id(UUID.randomUUID()).build()).build();
        UpdateBookingRequest request = new UpdateBookingRequest(null, LocalDateTime.now().plusDays(1), null, null, null);

        when(tableBookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(tableBookingRepository.findConflictingBookings(eq(1), any(), any()))
                .thenReturn(List.of(TableBooking.builder().id(UUID.randomUUID()).build()));

        assertThatThrownBy(() -> tableBookingService.updateBooking(id, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("not available");
    }

    @Test
    void updateBooking_UserNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        UpdateBookingRequest request = new UpdateBookingRequest(null, null, null, null, UUID.randomUUID());
        TableBooking booking = TableBooking.builder().id(id).user(User.builder().id(UUID.randomUUID()).build()).build();

        when(tableBookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tableBookingService.updateBooking(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id");
    }

    @Test
    void deleteBooking_BookingExists_DeletesSuccessfully() {
        UUID id = UUID.randomUUID();
        when(tableBookingRepository.existsById(id)).thenReturn(true);

        tableBookingService.deleteBooking(id);

        verify(tableBookingRepository).deleteById(id);
    }

    @Test
    void deleteBooking_BookingNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(tableBookingRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> tableBookingService.deleteBooking(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Booking not found with id");
    }
}
