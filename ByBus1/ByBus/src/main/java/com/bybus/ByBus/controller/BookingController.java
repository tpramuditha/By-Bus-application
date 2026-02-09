package com.bybus.ByBus.controller;

import com.bybus.ByBus.dto.BookingRequest;
import com.bybus.ByBus.entity.Booking;
import com.bybus.ByBus.repository.TimetableRepository;
import com.bybus.ByBus.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private TimetableRepository timetableRepository;
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= CREATE BOOKING =================
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    // ================= GET BOOKINGS BY PASSENGER =================
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Booking>> getBookingsByPassenger(
            @PathVariable Long passengerId) {
        return ResponseEntity.ok(bookingService.getBookingsByPassenger(passengerId));
    }

    // ================= CREATE BOOKING =================
    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody BookingRequest request) {
        Booking saved = bookingService.createBooking(request);
        return ResponseEntity.ok(saved);
    }


}
