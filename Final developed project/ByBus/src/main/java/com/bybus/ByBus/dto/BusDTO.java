package com.bybus.ByBus.dto;

import lombok.Data;

@Data
public class BusDTO {
    private Long id;
    private String busNumber;
    private String routeName;
    private String type;
    private String status;
    private int totalSeats;
    private Long driverId;
}
