package com.bybus.ByBus.service;

import com.bybus.ByBus.dto.BusDTO;
import com.bybus.ByBus.entity.Bus;

import java.util.List;

public interface BusService {
    // Create a new bus
    Bus createBus(BusDTO busDTO);

    //Update existing bus
    Bus updateBus(Long busId, BusDTO busDTO);

    //Delete a bus by ID
    void deleteBus(Long busId);

    //Get bus by ID
    Bus getBusById(Long busId);

    //Get all buses
    List<Bus> getAllBuses();

    List<Bus> getActiveBuses();
    List<Bus> searchByRoute(String start, String end);
}
