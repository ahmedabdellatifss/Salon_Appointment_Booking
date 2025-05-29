package com.roaa.service_offering.controller;


import com.roaa.service_offering.model.ServiceOffering;
import com.roaa.service_offering.service.ServiceOfferingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

    private ServiceOfferingService  serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    )
    {
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long id) throws Exception{
        ServiceOffering service = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(service);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceByIds(@PathVariable Set<Long> ids)
    {
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }

}
