package com.roaa.category.controller;


import com.roaa.category.dto.SalonDTO;
import com.roaa.category.model.Category;
import com.roaa.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> CreateCategory(@RequestBody Category category) {

        SalonDTO salonDTO  = new SalonDTO();
        salonDTO.setId(1L);
        Category savedCategory = categoryService.saveCategroy(category, salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteCategory(@PathVariable Long id) throws Exception {

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        categoryService.deleteCategoryById(id, salonDTO.getId());

        return ResponseEntity.ok("Deleted Category successfully");
    }
}
