package com.example.productmanagement.services;

import com.example.productmanagement.dto.CategoryDto;
import com.example.productmanagement.dto.ProduitDto;
import com.example.productmanagement.entities.Category;
import com.example.productmanagement.entities.Produit;
import com.example.productmanagement.exception.RessourceNotFoundException;
import com.example.productmanagement.repositories.CategoryRepository;
import com.example.productmanagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryService=categoryService;
    }

    public List<ProduitDto> getallproducts (){
        try{
        List<Produit> produits = new ArrayList<>();
        produits=productRepository.findAllByisDeleted(false);
        List<ProduitDto> produitDtoList=new ArrayList<>();
        for (Produit produit : produits) {
            ProduitDto produitDto=getProduitDto(produit);
            CategoryDto categoryDto = categoryService.getCategoryDto(produit.getCategory()) ;
            produitDto.setCategory_id(categoryService.toEntity(categoryDto).getId());
            produitDtoList.add(produitDto);
        }


            return produitDtoList;}
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch products from the database.");
        }
    }
    public ProduitDto getProduct (Long id){
        try {

            Produit produit = productRepository.findById(id)
                    .orElseThrow(() -> new RessourceNotFoundException("Product with ID " + id + " not found"));
            ProduitDto produitDto=getProduitDto(produit);
            return produitDto ;
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new RessourceNotFoundException("Product with ID " + id + " not found");
        }
    }
    public Produit insertproduct (ProduitDto produitDto){
             try {
            // Check if a product with the same name already exists in the database.

            boolean productExists =productRepository.existsByname(produitDto.getName());
            if (productExists) {
                // Handle the case when the product with the same name already exists.
                // You can throw an exception or return null or any other action you want.
                // For example:
                throw new IllegalArgumentException("Product with the same name already exists.");
            }
            // Fetch the associated Categorie from the database using its ID.

            // Set the fetched Categorie in the Produit.
            Produit produit = toEntity(produitDto);

            // Save the product to the database.
            return productRepository.save(produit);}
             catch(Exception ex){
                 ex.printStackTrace(); // Just an example, you should use a proper logging mechanism.
                 throw new RuntimeException("Error inserting product " + ex.getMessage());
             }
    }



    public void deleteproduct (long id){

           try {

            Produit produit = productRepository.findById(id).orElse(null);
            if (produit == null)
                throw new RessourceNotFoundException("Product with ID " + id + " not found.");


        productRepository.deleteById(id);}
           catch (Exception ex){
               throw new RuntimeException("error updating");
           }
        }
    public Produit updateProduit(ProduitDto updatedProduitDto , Long id) {
        // Check if the produit with the given ID exists
        try{
        Produit existingProduit = productRepository.findById(id).orElse(null);
        if (existingProduit == null)
            throw new RessourceNotFoundException("Product with ID " + id + " not found.");

            // Update the existing produit with the new values
            Produit produit = toEntity(updatedProduitDto);

        produit.setDatemodification(new Date());
        // Save the updated produit to the database
        return productRepository.save(produit);}
        catch (Exception ex){
            throw new RuntimeException("error updating");
        }
    }
    public Produit archiverProduit(long id , Produit updatedproduit) {
        // Check if the categorie with the given ID exists
       try{

        Produit existingProduit = productRepository.findById(id).orElse(null);
        if (existingProduit == null)
            throw new RessourceNotFoundException("Product with ID " + id + " not found.");

        // Update the existing categorie with the new values
        existingProduit.setDeleted(true);


        // Save the updated categorie to the database
        return productRepository.save(existingProduit);
    }
       catch (Exception ex){
           throw new RuntimeException("error updating");
       }}
    public Produit activerProduit( long id ,Produit updatedproduit) {
        // Check if the categorie with the given ID exists
        try {


        Produit existingproduit= productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit with ID " + updatedproduit.getId() + " not found"));

        // Update the existing categorie with the new values
        existingproduit.setDeleted(false);


        // Save the updated categorie to the database
        return productRepository.save(existingproduit);
    }
        catch (Exception ex){
            throw new RuntimeException("error updating");
        }


    }

    private Produit toEntity(ProduitDto produitDto) {
        return new Produit(
                produitDto.getId(),
                produitDto.getName(),
                produitDto.getPrice(),
                new Category(produitDto.getCategory_id()));
    }
    private ProduitDto getProduitDto(Produit produit){
        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(produit.getId());
        produitDto.setName(produit.getName());
        produitDto.setPrice(produit.getPrice());
        produitDto.setCategory_id(produit.getCategory().getId());

        return produitDto ;
    }

}
