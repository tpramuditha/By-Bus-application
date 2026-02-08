package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.dto.TimetableDTO;
import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.entity.Timetable;
import com.bybus.ByBus.repository.BusRepository;
import com.bybus.ByBus.repository.TimetableRepository;
import com.bybus.ByBus.service.TimetableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final BusRepository busRepository;


    // Constructor Injection
    public TimetableServiceImpl(TimetableRepository timetableRepository,
                                BusRepository busRepository) {
        this.timetableRepository = timetableRepository;
        this.busRepository = busRepository;
    }

    // ================= CREATE TIMETABLE =================
    @Override
    public Timetable createTimetable(TimetableDTO dto) {

        Bus bus = busRepository.findById(dto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        Timetable timetable = new Timetable();
        timetable.setBus(bus);
        timetable.setStartLocation(dto.getStartLocation());
        timetable.setEndLocation(dto.getEndLocation());
        timetable.setStartTime(dto.getStartTime());
        timetable.setEndTime(dto.getEndTime());

        return timetableRepository.save(timetable);
    }

    // ================= UPDATE TIMETABLE =================
    @Override
    public Timetable updateTimetable(Long timetableId, TimetableDTO dto) {

        Timetable timetable = timetableRepository.findById(timetableId)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));

        timetable.setStartLocation(dto.getStartLocation());
        timetable.setEndLocation(dto.getEndLocation());
        timetable.setStartTime(dto.getStartTime());
        timetable.setEndTime(dto.getEndTime());

        if (dto.getBusId() != null) {
            Bus bus = busRepository.findById(dto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            timetable.setBus(bus);
        }

        return timetableRepository.save(timetable);
    }

    // ================= GET TIMETABLE BY ID =================
    @Override
    public Timetable getTimetableById(Long timetableId) {
        return timetableRepository.findById(timetableId)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));
    }

    // ================= GET ALL TIMETABLES =================
    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }


    // ================= DELETE TIMETABLE =================
    @Override
    public void deleteTimetable(Long timetableId) {
        timetableRepository.deleteById(timetableId);
    }
}
