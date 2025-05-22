package com.roaa.salon.repository;

import com.roaa.salon.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SalonRepository extends JpaRepository<Salon, Long> {

    Salon findByOwnerId(Long ownerId);

    @Query(
            "select s from Salon s where" +
                    "(lower(s.city) like lower(concat('%', :keyword, '%')) OR " +
                    "lower(s.name) like lower(concat('%', :keyword, '%')) OR " +
                    "lower(s.address) like lower(concat('%', :keyword, '%')))"
    )
    List<Salon> searchSalon(@Param("keyword") String keyword);

}
