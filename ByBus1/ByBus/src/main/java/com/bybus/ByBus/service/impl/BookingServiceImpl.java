package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.dto.BookingRequest;
import com.bybus.ByBus.entity.Booking;
import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.entity.User;
import com.bybus.ByBus.repository.BookingRepository;
import com.bybus.ByBus.repository.BusRepository;
import com.bybus.ByBus.repository.UserRepository;
import com.bybus.ByBus.service.BookingService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, BusRepository busRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
    }

    // ================= CREATE BOOKING =================
    @Override
    public Booking createBooking(BookingRequest request) {

        if (request.getPassengerId() == null) throw new RuntimeException("Passenger required");
        if (request.getBusId() == null) throw new RuntimeException("Bus required");
        if (request.getTravelDate() == null) throw new RuntimeException("Travel date required");
        if (request.getSeatCount() < 1) throw new RuntimeException("Seat count required");

        Bus bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        //  passenger from login (Spring Security)
        User passenger = userRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));


        Booking booking = new Booking();
        booking.setBus(bus);
        booking.setPassenger(passenger);
        booking.setTravelDate(request.getTravelDate());
        booking.setSeatCount(request.getSeatCount());
        booking.setBookingTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    // ================= GET BOOKING BY ID =================
    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // ================= GET BOOKINGS BY PASSENGER =================
    @Override
    public List<Booking> getBookingsByPassenger(Long passengerId) {
        if (passengerId == null) {
            throw new IllegalArgumentException("Passenger ID must not be null");
        }

        User passenger = userRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        return bookingRepository.findAll()
                .stream()
                .filter(b -> b.getPassenger() != null &&
                        b.getPassenger().getId().equals(passenger.getId()))
                .toList();
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ================= For booking table Status colum =================
    @Override
    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!status.equals("CONFIRMED") && !status.equals("CANCELLED") && !status.equals("NOT_CONFIRMED")) {
            throw new RuntimeException("Invalid status");
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

}
