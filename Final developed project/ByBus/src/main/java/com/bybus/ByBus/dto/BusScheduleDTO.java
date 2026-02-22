package com.bybus.ByBus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusScheduleDTO {
    private Long timetableId;
    private Long busId;
    private String busNumber;
    private String startLocation;
    private String endLocation;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type;
    private String status;

}
