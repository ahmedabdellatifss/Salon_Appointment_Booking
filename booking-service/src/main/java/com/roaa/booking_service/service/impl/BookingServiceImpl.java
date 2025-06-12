package com.roaa.booking_service.service.impl;

import com.roaa.booking_service.domain.BookingStatus;
import com.roaa.booking_service.dto.BookingRequest;
import com.roaa.booking_service.dto.SalonDTO;
import com.roaa.booking_service.dto.ServiceDTO;
import com.roaa.booking_service.dto.UserDTO;
import com.roaa.booking_service.model.SalonReport;
import com.roaa.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.roaa.booking_service.model.Booking;
import com.roaa.booking_service.service.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    
    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception {

        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);

    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        LocalDateTime salonOpenTime = LocalDateTime.from(salonDTO.getOpenTime());
        LocalDateTime salonCloseTime = LocalDateTime.from(salonDTO.getCloseTime());
        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)){
            throw new Exception("Booking time must be within salon's working hour");
        }

        for(Booking booking : existingBookings){
            LocalDateTime existingBookingStartTime = booking.getStartTime();
            LocalDateTime existingBookingEndTime = booking.getEndTime();

            if(bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("Slot not available, choose different time.");
            }
            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception("Slot not available, choose different time.");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        
        return bookingRepository.findBySalonId(salonId);
    }
    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new Exception("booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking =  getBookingById(bookingId);

        booking.setStatus(status);
        return bookingRepository.save(booking);

    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);

        if(date == null){
            return allBookings;
        }
        return allBookings.stream()
                .filter(booking ->
                        isSameDte(booking.getStartTime(), date) ||
                        isSameDte(booking.getEndTime(), date))
                .toList();
    }

    private boolean isSameDte(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();
        Integer totalBooking = bookings.size();
        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED) )
                .toList();
        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalEarnings(totalEarnings);
        report.setTotalBookings(totalBooking);
        report.setTotalRefund(totalRefund);
        report.setCancelledBookings(cancelledBookings.size());


        return report;  
    }


}
