package com.bybus.ByBus.repository;

import com.bybus.ByBus.dto.BusScheduleDTO;
import com.bybus.ByBus.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable,Long> {

    @Query("""
        select new com.bybus.ByBus.dto.BusScheduleDTO(
            t.id,
            b.id,
            b.busNumber,
            t.startLocation,
            t.endLocation,
            t.startTime,
            t.endTime,
            b.type,
            b.status
        )
        from Timetable t
        join t.bus b
        where b.status = 'ACTIVE'
        """)

    List<BusScheduleDTO> findActiveSchedules();

    @Query("""
        select new com.bybus.ByBus.dto.BusScheduleDTO(
            t.id,
            b.id,
            b.busNumber,
            t.startLocation,
            t.endLocation,
            t.startTime,
            t.endTime,
            b.type,
            b.status
        )
        from Timetable t
        join t.bus b
        where b.status = 'ACTIVE'
          and lower(t.startLocation) like lower(concat('%', :start, '%'))
          and lower(t.startLocation) like lower(concat('%', :start, '%'))
        """)

    List<BusScheduleDTO> findActiveSchedulesByLocations(@Param("start") String start,
                                                        @Param("end") String end);

    List<Timetable> findByStartLocationContainingIgnoreCase(String start);

    List<Timetable> findByEndLocationContainingIgnoreCase(String end);

    List<Timetable> findByStartLocationContainingIgnoreCaseAndEndLocationContainingIgnoreCase(String start, String end);

}
