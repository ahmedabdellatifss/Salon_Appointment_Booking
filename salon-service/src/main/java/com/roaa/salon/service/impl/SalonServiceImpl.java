package com.roaa.salon.service.impl;

import com.roaa.salon.model.Salon;
import com.roaa.salon.payload.dto.SalonDTO;
import com.roaa.salon.payload.dto.UserDTO;
import com.roaa.salon.repository.SalonRepository;
import com.roaa.salon.service.SalonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {

        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());

        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception{
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (existingSalon != null &&salon.getOwnerId().equals(user.getId())) {
            existingSalon.setName(salon.getName());
            existingSalon.setCity(salon.getCity());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOwnerId(user.getId());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());

            return salonRepository.save(existingSalon);

        }
        throw new Exception("Salon with id " + salonId + " not found");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception{
        Salon salon =  salonRepository.findById(salonId).orElse(null);
        if (salon == null) {
            throw new Exception("Salon with id " + salonId + " not found");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalon(city);
    }
}
