package com.roaa.service_offering.repository;

import com.roaa.service_offering.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering,Long> {

    Set<ServiceOffering> findBySalonId(Long salonId);

}
