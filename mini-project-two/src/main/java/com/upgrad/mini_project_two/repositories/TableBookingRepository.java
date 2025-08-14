package com.upgrad.mini_project_two.repositories;


import com.upgrad.mini_project_two.entities.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, UUID> {
    List<TableBooking> findByTableNumber(Integer tableNumber);

    @Query("SELECT b FROM TableBooking b WHERE b.tableNumber = :tableNumber AND " +
            "b.bookingTime BETWEEN :startTime AND :endTime")
    List<TableBooking> findConflictingBookings(
            @Param("tableNumber") Integer tableNumber,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    List<TableBooking> findByBookingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}