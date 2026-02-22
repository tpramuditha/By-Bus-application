package com.bybus.ByBus.controller;

import com.bybus.ByBus.dto.BusScheduleDTO;
import com.bybus.ByBus.repository.TimetableRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/passenger/schedules")
public class PassengerScheduleController {

    private final TimetableRepository timetableRepository;

    public PassengerScheduleController(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    @GetMapping
    public ResponseEntity<List<BusScheduleDTO>> getAllActiveSchedules() {
        return ResponseEntity.ok(timetableRepository.findActiveSchedules());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusScheduleDTO>> search(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseEntity.ok(
                timetableRepository.findActiveSchedulesByLocations(start, end)
        );
    }

}
