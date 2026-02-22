package com.bybus.ByBus.repository;

import com.bybus.ByBus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus,Long> {
    List<Bus> findByStatus(String status);

    List<Bus> findByRouteNameContainingIgnoreCase(String route);

}
