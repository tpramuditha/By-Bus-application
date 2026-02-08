package com.bybus.ByBus.service;

import com.bybus.ByBus.dto.TimetableDTO;
import com.bybus.ByBus.entity.Timetable;

import java.util.List;

public interface TimetableService {

    // Create a new timetable for a bus
    Timetable createTimetable(TimetableDTO timetableDTO);

    // Update an existing timetable
    Timetable updateTimetable(Long timetableId, TimetableDTO timetableDTO);

    // Get timetable by ID
    Timetable getTimetableById(Long timetableId);

    // Get all timetables
    List<Timetable> getAllTimetables();

    //Delete timetable by ID
    void deleteTimetable(Long timetableId);
}
