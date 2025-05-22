package com.roaa.salon.controller;

import com.roaa.salon.mapper.SalonMapper;
import com.roaa.salon.model.Salon;
import com.roaa.salon.payload.dto.SalonDTO;
import com.roaa.salon.payload.dto.UserDTO;
import com.roaa.salon.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping()
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable Long salonId,
            @RequestBody SalonDTO salonDTO) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalon() {


        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream().map(salon -> SalonMapper.mapToDTO(salon)).collect(Collectors.toList());
        System.out.println("salonDTOS: " + salonDTOS);
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalon(@PathVariable Long salonId) throws Exception {
        Salon salon = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) {

        List<Salon> salons = salonService.searchSalonByCity(city);

        List<SalonDTO> salonDTOS = salons.stream().map(SalonMapper::mapToDTO).toList();

        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner/{ownerID}")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long ownerID) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salons = salonService.getSalonByOwnerId(userDTO.getId());

        SalonDTO salonDTO = SalonMapper.mapToDTO(salons);

        return ResponseEntity.ok(salonDTO);
    }



}
