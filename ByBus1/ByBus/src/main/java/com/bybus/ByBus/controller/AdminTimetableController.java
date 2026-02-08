package com.bybus.ByBus.controller;

import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.entity.Timetable;
import com.bybus.ByBus.repository.BusRepository;
import com.bybus.ByBus.repository.TimetableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin/timetables")
public class AdminTimetableController {
    private final TimetableRepository timetableRepository;
    private final BusRepository busRepository;

    public AdminTimetableController(TimetableRepository timetableRepository, BusRepository busRepository) {
        this.timetableRepository = timetableRepository;
        this.busRepository = busRepository;
    }

    //  list all
    @GetMapping
    public List<Timetable> getAll() {
        return timetableRepository.findAll();
    }

    //  filter by destination / route
    // example: /api/admin/timetables/search?start=Colombo&end=Kandy
    @GetMapping("/search")
    public List<Timetable> search(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end
    ) {
        if ((start == null || start.isBlank()) && (end == null || end.isBlank())) {
            return timetableRepository.findAll();
        }
        if (start != null && !start.isBlank() && end != null && !end.isBlank()) {
            return timetableRepository.findByStartLocationContainingIgnoreCaseAndEndLocationContainingIgnoreCase(start, end);
        }
        if (start != null && !start.isBlank()) {
            return timetableRepository.findByStartLocationContainingIgnoreCase(start);
        }
        return timetableRepository.findByEndLocationContainingIgnoreCase(end);
    }

    //  create
    @PostMapping
    public Timetable create(@RequestBody Timetable t) {
        validate(t);

        Long busId = t.getBus().getId();
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bus id"));

        t.setId(null);
        t.setBus(bus);
        return timetableRepository.save(t);
    }

    //  update
    @PutMapping("/{id}")
    public Timetable update(@PathVariable Long id, @RequestBody Timetable t) {
        Timetable existing = timetableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable not found"));

        validate(t);

        Long busId = t.getBus().getId();
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bus id"));

        existing.setBus(bus);
        existing.setStartLocation(t.getStartLocation());
        existing.setEndLocation(t.getEndLocation());
        existing.setStartTime(t.getStartTime());
        existing.setEndTime(t.getEndTime());

        return timetableRepository.save(existing);
    }

    //  delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!timetableRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable not found");
        }
        timetableRepository.deleteById(id);
    }

    private void validate(Timetable t) {
        if (t.getBus() == null || t.getBus().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus is required");
        }

        if (t.getStartLocation() == null || t.getStartLocation().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start location required");
        }

        if (t.getEndLocation() == null || t.getEndLocation().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End location required");
        }

        if (t.getStartTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time required");
        }

        if (t.getEndTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time required");
        }
    }
}
