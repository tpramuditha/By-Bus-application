package com.bybus.ByBus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // passenger (User)
    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id")
    private User passenger;

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    // bus
    @ManyToOne(optional = false)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    private LocalDate travelDate;

    private int seatCount;

    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private String status = "NOT_CONFIRMED";

}
