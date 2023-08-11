package com.example.productmanagement.services;

import com.example.productmanagement.dto.CategoryDto;
import com.example.productmanagement.entities.Category;
import com.example.productmanagement.exception.RessourceNotFoundException;
import com.example.productmanagement.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getallcategories (){
        try {
            List<Category> categories = new ArrayList<>();
            categories=categoryRepository.findAll();
            List<CategoryDto> categoryDtoList=new ArrayList<>();
            for (Category category : categories) {
                CategoryDto categoryDto=getCategoryDto(category);
                categoryDtoList.add(categoryDto);
            }


            return categoryDtoList;}
        catch (Exception ex){
         ex.printStackTrace();
         throw new RessourceNotFoundException("Failed to fetch categories from the database.");
        }

    }
    public CategoryDto getCategory (Long id){
        try {

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RessourceNotFoundException("category with ID " + id + " not found"));
            CategoryDto categoryDto=getCategoryDto(category);
            return categoryDto ;
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new RessourceNotFoundException("Category with ID " + id + " not found");
        }

    }

    public Category insertcategory (CategoryDto categoryDto){
        try {
            // Check if a product with the same name already exists in the database.

            boolean categoryExists =categoryRepository.existsByname(categoryDto.getName());
            if (categoryExists) {
                // Handle the case when the product with the same name already exists.
                // You can throw an exception or return null or any other action you want.
                // For example:
                throw new RessourceNotFoundException("Product with the same name already exists.");
            }
            Category category=toEntity(categoryDto);
            // Save the product to the database.
            return categoryRepository.save(category);}
        catch (Exception ex) {
                // Handle the exception here or rethrow it if needed.
                // For example, you can log the error or throw a custom exception.
                ex.printStackTrace(); // Just an example, you should use a proper logging mechanism.
                throw new RuntimeException("Error inserting category: " + ex.getMessage());
            }

        }

    public void deletecategory (Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(()->new RessourceNotFoundException("ID :" + id +" does not exist in the database."));
            categoryRepository.delete(category);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RessourceNotFoundException("ID :" + id +" does not exist in the database.");
        }
    }


    public Category updateCategory( long id ,CategoryDto updatedcategoryDto) {
        // Check if the categorie with the given ID exists
        try {
            Category existingCategory = categoryRepository.findById(id)
                    .orElseThrow(() -> new RessourceNotFoundException("Categorie with ID " + id + " not found"));

            // Update the existing categorie with the new values
            existingCategory.setName(updatedcategoryDto.getName());
            existingCategory.setDatemodification(new Date());


            // Save the updated categorie to the database
            return categoryRepository.save(existingCategory);
        } catch (Exception ex) {
            throw new RuntimeException("error updating");
        }
    }
    public Category archiverCategory( long id ,Category updatedcategory) {
        // Check if the categorie with the given ID exists
        try{
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Categorie with ID " + updatedcategory.getId() + " not found"));

        // Update the existing categorie with the new values
        existingCategory.setDeleted(true);


        // Save the updated categorie to the database
        return categoryRepository.save(existingCategory);
    }
        catch(Exception ex){
            throw new RuntimeException("error updating");
        }

        }
    public Category activeracategory( long id ,Category updatedcategory) {
        // Check if the categorie with the given ID exists
       try{
        Category existingCategory= categoryRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Categorie with ID " + updatedcategory.getId() + " not found"));

        // Update the existing categorie with the new values
        existingCategory.setDeleted(false);


        // Save the updated categorie to the database
        return categoryRepository.save(existingCategory);
    }
       catch (Exception ex){
           throw new RuntimeException("error updating");
       }}

    protected Category toEntity(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );

    }
    protected CategoryDto getCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto ;
    }


}
