package com.roaa.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalonDTO {

    private Long id;

    private String name;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}