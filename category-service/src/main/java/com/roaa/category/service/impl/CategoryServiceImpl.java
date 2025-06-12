package com.roaa.category.service.impl;

import com.roaa.category.dto.SalonDTO;
import com.roaa.category.model.Category;
import com.roaa.category.repository.CategoryRepository;
import com.roaa.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategroy(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setId(category.getId());
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());

        return categoryRepository.save(newCategory);

    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null) {
            throw new Exception("category not exist with id " + id);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {

        Category category = getCategoryById(id);

        if(!Objects.equals(category.getSalonId(), salonId)) {
            throw new Exception("you don't have permission to delete category" );
        }
        categoryRepository.deleteById(id);
    }
}
