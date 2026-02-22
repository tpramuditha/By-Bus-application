package com.bybus.ByBus.controller;

import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.service.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class PassengerBusController {
    private final BusService busService;

    public PassengerBusController(BusService busService) {
        this.busService = busService;
    }

    // PASSENGER: view active buses only
    @GetMapping
    public ResponseEntity<List<Bus>> getActiveBuses() {
        return ResponseEntity.ok(busService.getActiveBuses());
    }

    // PASSENGER: search buses
    @GetMapping("/search")
    public ResponseEntity<List<Bus>> searchBuses(
            @RequestParam String start,
            @RequestParam String end) {

        return ResponseEntity.ok(
                busService.searchByRoute(start, end)
        );
    }

    // PASSENGER: view one bus
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }
}
