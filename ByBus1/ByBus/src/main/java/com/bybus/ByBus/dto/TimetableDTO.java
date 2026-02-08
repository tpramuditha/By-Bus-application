package com.bybus.ByBus.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimetableDTO {
    private Long busId;
    private String startLocation;
    private String endLocation;
    private LocalTime startTime;
    private LocalTime endTime;
}
