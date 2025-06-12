package com.roaa.booking_service.service;

import com.roaa.booking_service.domain.BookingStatus;
import com.roaa.booking_service.dto.BookingRequest;
import com.roaa.booking_service.dto.SalonDTO;
import com.roaa.booking_service.dto.ServiceDTO;
import com.roaa.booking_service.dto.UserDTO;
import com.roaa.booking_service.model.Booking;
import com.roaa.booking_service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.cglib.core.Local;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO userDTO,
                          SalonDTO  salonDTO,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date,Long salonId);

    SalonReport getSalonReport(Long salonId);





}
