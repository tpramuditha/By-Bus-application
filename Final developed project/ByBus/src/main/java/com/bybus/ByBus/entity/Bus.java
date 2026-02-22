package com.bybus.ByBus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String busNumber;
    private String routeName;
    private String type;
    private String status;
    private int totalSeats;

}
// Constructors, getters, setters for use Lombok (@Data)