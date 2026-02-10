package com.bybus.ByBus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BookingHistoryDTO {
    private Long bookingId;
    private String busNumber;
    private String startLocation;
    private String endLocation;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate travelDate;
    private int seatCount;
    private String status;
}
