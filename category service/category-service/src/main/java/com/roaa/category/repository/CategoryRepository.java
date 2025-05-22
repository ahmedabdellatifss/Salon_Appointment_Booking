package com.roaa.category.repository;

import com.roaa.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Set<Category> findBySalonId(Long salonId);
}
