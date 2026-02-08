package com.bybus.ByBus.controller;

import com.bybus.ByBus.dto.TimetableDTO;
import com.bybus.ByBus.entity.Timetable;
import com.bybus.ByBus.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    // Constructor injection
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    // ================= CREATE TIMETABLE =================
    @PostMapping
    public ResponseEntity<Timetable> createTimetable(
            @RequestBody TimetableDTO timetableDTO) {

        Timetable timetable = timetableService.createTimetable(timetableDTO);
        return ResponseEntity.ok(timetable);
    }

    // ================= UPDATE TIMETABLE =================
    @PutMapping("/{id}")
    public ResponseEntity<Timetable> updateTimetable(
            @PathVariable Long id,
            @RequestBody TimetableDTO timetableDTO) {

        Timetable timetable = timetableService.updateTimetable(id, timetableDTO);
        return ResponseEntity.ok(timetable);
    }

    // ================= GET TIMETABLE BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Timetable> getTimetableById(@PathVariable Long id) {
        Timetable timetable = timetableService.getTimetableById(id);
        return ResponseEntity.ok(timetable);
    }

    // ================= GET ALL TIMETABLES =================
    @GetMapping
    public ResponseEntity<List<Timetable>> getAllTimetables() {
        return ResponseEntity.ok(timetableService.getAllTimetables());
    }

    // ================= DELETE TIMETABLE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return ResponseEntity.ok("Timetable deleted successfully");
    }
}
