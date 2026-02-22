package com.bybus.ByBus.service;

import com.bybus.ByBus.dto.BookingRequest;
import com.bybus.ByBus.entity.Booking;

import java.util.List;

public interface BookingService {
    //Create a new booking (ticket booking)
    Booking createBooking(BookingRequest request);

    //Get booking by ID
    Booking getBookingById(Long bookingId);

    // Get all bookings of a passenger
    List<Booking> getBookingsByPassenger(Long passengerId);

    List<Booking> getAllBookings();
    Booking updateBookingStatus(Long bookingId, String status);
}
