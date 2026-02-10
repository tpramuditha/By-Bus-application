package com.bybus.ByBus.controller;

import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.repository.BusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buses")
public class AdminBusController {
    private final BusRepository busRepository;

    public AdminBusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @GetMapping
    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bus getOne(@PathVariable Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found"));
    }

    @PostMapping
    public Bus create(@RequestBody Bus bus) {
        validate(bus);
        bus.setId(null); // ensure insert
        return busRepository.save(bus);
    }

    @PutMapping("/{id}")
    public Bus update(@PathVariable Long id, @RequestBody Bus bus) {
        Bus existing = busRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found"));

        // Update only the fields that are sent (not null / not blank)
        if (bus.getBusNumber() != null && !bus.getBusNumber().isBlank()) {
            existing.setBusNumber(bus.getBusNumber());
        }

        if (bus.getRouteName() != null && !bus.getRouteName().isBlank()) {
            existing.setRouteName(bus.getRouteName());
        }

        if (bus.getType() != null && !bus.getType().isBlank()) {
            existing.setType(bus.getType());
        }

        if (bus.getStatus() != null && !bus.getStatus().isBlank()) {
            existing.setStatus(bus.getStatus());
        }

        return busRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!busRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found");
        }
        busRepository.deleteById(id);
    }

    private void validate(Bus bus) {
        if (bus.getBusNumber() == null || bus.getBusNumber().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus number is required");

        if (bus.getRouteName() == null || bus.getRouteName().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Route is required");

        if (bus.getType() == null || bus.getType().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type is required");

        if (bus.getStatus() == null || bus.getStatus().isBlank())
            bus.setStatus("ACTIVE");
    }
}
