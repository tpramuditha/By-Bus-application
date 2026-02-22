package com.bybus.ByBus.repository;

import com.bybus.ByBus.dto.BookingHistoryDTO;
import com.bybus.ByBus.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    long countByBus_IdAndTravelDate(Long busId, LocalDate travelDate);

    @Query("""
    select new com.bybus.ByBus.dto.BookingHistoryDTO(
        bk.id,
        b.busNumber,
        t.startLocation,
        t.endLocation,
        t.startTime,
        t.endTime,
        bk.travelDate,
        bk.seatCount,
        bk.status
    )
    from Booking bk
    join bk.bus b
    join bk.timetable t
    where bk.passenger.id = :pid
    order by bk.id desc
""")
    List<BookingHistoryDTO> findBookingHistory(@Param("pid") Long passengerId);

}
