package com.bybus.ByBus.dto;

import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long passengerId;
    private Long busId;
    private Long timetableId;
    private LocalDate travelDate;
    private int seatCount;

}
