package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.dto.BusDTO;
import com.bybus.ByBus.entity.Bus;
import com.bybus.ByBus.entity.User;
import com.bybus.ByBus.repository.BusRepository;
import com.bybus.ByBus.repository.UserRepository;
import com.bybus.ByBus.service.BusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl  implements BusService {
    private final BusRepository busRepository;


    // Constructor Injection
   public BusServiceImpl(BusRepository busRepository, UserRepository userRepository) {
       this.busRepository = busRepository;
   }

    // ================= CREATE BUS =================
    @Override
    public Bus createBus(BusDTO busDTO) {
        Bus bus = new Bus();
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setRouteName(busDTO.getRouteName());
        bus.setTotalSeats(busDTO.getTotalSeats());

        return busRepository.save(bus);
    }

    // ================= UPDATE BUS =================
    @Override
    public Bus updateBus(Long busId, BusDTO busDTO) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(()->new RuntimeException("Bus not found"));
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setRouteName(busDTO.getRouteName());
        bus.setTotalSeats(busDTO.getTotalSeats());

        return busRepository.save(bus);
    }

    // ================= DELETE BUS =================
    @Override
    public void deleteBus(Long busId) {
       busRepository.deleteById(busId);
    }

    // ================= GET BUS BY ID =================
    @Override
    public Bus getBusById(Long busId) {
       return busRepository.findById(busId)
                .orElseThrow(()->new RuntimeException("Bus not found"));
    }

    // ================= GET ALL BUSES =================
    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // =========Get active buses=====
    @Override
    public List<Bus> getActiveBuses() {
        return busRepository.findByStatus("ACTIVE");
    }

    @Override
    public List<Bus> searchByRoute(String start, String end) {
        // example: "Colombo - Kandy"
        String route = start.trim() + " - " + end.trim();
        return busRepository.findByRouteNameContainingIgnoreCase(route);
    }

}
