package com.roaa.booking_service.controller;


import com.roaa.booking_service.domain.BookingStatus;
import com.roaa.booking_service.dto.*;
import com.roaa.booking_service.mapper.BookingMapper;
import com.roaa.booking_service.model.Booking;
import com.roaa.booking_service.model.SalonReport;
import com.roaa.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
    ) throws Exception {

         UserDTO user = new UserDTO();
         user.setId(1L);


        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalDateTime.now());
        salon.setCloseTime(LocalDateTime.now().plusHours(12));

        Set<ServiceDTO> servicesDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        servicesDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, servicesDTOSet);

        return  ResponseEntity.ok(booking);


    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(

    ){

        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(

    ){

        List<Booking> bookings = bookingService.getBookingsBySalon(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings)
    {
        return bookings.stream().map(BookingMapper::toDTO).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
        @PathVariable Long bookingId
    ) throws Exception {

        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId, status);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookingSlot(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date

    ) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> slotsDTOs = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).toList();

        return ResponseEntity.ok(slotsDTOs);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {

        SalonReport report = bookingService.getSalonReport(1L);

        return ResponseEntity.ok(report);
    }


}
