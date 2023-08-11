package com.example.productmanagement.controllers;

import com.example.productmanagement.dto.CategoryDto;
import com.example.productmanagement.entities.Category;
import com.example.productmanagement.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@CrossOrigin("*")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getallcategories() {
        return categoryService.getallcategories();
    }
    @GetMapping("/{id}")
    public void getCategory(@PathVariable Long id) {
        categoryService.getCategory(id);
    }
    @PostMapping
    public Category insertCategory(@RequestBody CategoryDto category) {
        return categoryService.insertcategory(category);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deletecategory(id);
    }
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryDto categorie) {
        // Set the categorieId to the updatedcategorie based on the ID provided in the path

        return categoryService.updateCategory(id, categorie);
    }
    @PutMapping("/archiver/{id}")
    public Category archiverCategory(@PathVariable Long id, @RequestBody Category categorie) {
        // Set the categorieId to the updatedcategorie based on the ID provided in the path

        return categoryService.archiverCategory(id, categorie);
    }
    @PutMapping("/activer/{id}")
    public Category activerCategory(@PathVariable Long id, @RequestBody Category categorie) {
        // Set the categorieId to the updatedcategorie based on the ID provided in the path

        return categoryService.activeracategory(id, categorie);
    }

}
