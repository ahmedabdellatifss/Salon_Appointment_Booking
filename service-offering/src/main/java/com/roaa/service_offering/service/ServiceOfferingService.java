package com.roaa.service_offering.service;

import com.roaa.service_offering.model.ServiceOffering;
import com.roaa.service_offering.payload.dto.CategoryDTO;
import com.roaa.service_offering.payload.dto.SalonDTO;
import com.roaa.service_offering.payload.dto.ServiceDTO;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDto, ServiceDTO serviceDto, CategoryDTO categoryDTo);

    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(long salonId, Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
