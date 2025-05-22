package com.roaa.category.service;

import com.roaa.category.dto.SalonDTO;
import com.roaa.category.model.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategroy(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoriesBySalonId(Long salonId);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long id, Long salonId) throws Exception;
}
