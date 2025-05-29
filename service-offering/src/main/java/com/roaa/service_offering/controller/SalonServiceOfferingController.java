package com.roaa.service_offering.controller;


import com.roaa.service_offering.model.ServiceOffering;
import com.roaa.service_offering.payload.dto.CategoryDTO;
import com.roaa.service_offering.payload.dto.SalonDTO;
import com.roaa.service_offering.payload.dto.ServiceDTO;
import com.roaa.service_offering.service.ServiceOfferingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping()
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
    ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        ServiceOffering serviceOffering = serviceOfferingService.createService(
                salonDTO, serviceDTO, categoryDTO
        );

        return ResponseEntity.ok(serviceOffering);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @RequestParam Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception {

        ServiceOffering serviceOffering1 = serviceOfferingService.updateService(
                id, serviceOffering
        );

        return ResponseEntity.ok(serviceOffering1);
    }

}
