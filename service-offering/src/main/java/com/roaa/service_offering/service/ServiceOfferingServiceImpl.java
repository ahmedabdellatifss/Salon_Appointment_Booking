package com.roaa.service_offering.service;

import com.roaa.service_offering.model.ServiceOffering;
import com.roaa.service_offering.payload.dto.CategoryDTO;
import com.roaa.service_offering.payload.dto.SalonDTO;
import com.roaa.service_offering.payload.dto.ServiceDTO;
import com.roaa.service_offering.repository.ServiceOfferingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDTO salonDto, ServiceDTO serviceDto, CategoryDTO categoryDTo) {

        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDto.getImage());
        serviceOffering.setName(serviceDto.getName());
        serviceOffering.setPrice(serviceDto.getPrice());
        serviceOffering.setDescription(serviceDto.getDescription());
        serviceOffering.setPrice(serviceDto.getPrice());
        serviceOffering.setDuration(serviceDto.getDuration());
        serviceOffering.setCategoryId(categoryDTo.getId());
        serviceOffering.setSalonId(salonDto.getId());

        return serviceOfferingRepository.save(serviceOffering);


    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);
        if(serviceOffering == null){
            throw new Exception("Service not exist with Id " + serviceId);
        }

        serviceOffering.setImage(service.getImage());
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(long salonId, Long categoryId) {

        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if(categoryId != null)
        {
            services = services.stream().filter(s -> s.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {

        List<ServiceOffering> service = serviceOfferingRepository.findAllById(ids);
        return  new HashSet<>(service);

    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);

        if(serviceOffering == null){
            throw new Exception("Service not exist with Id " + id);
        }

        return serviceOffering;
    }
}
