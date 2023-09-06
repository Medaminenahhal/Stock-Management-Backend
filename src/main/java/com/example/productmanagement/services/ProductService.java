package com.example.productmanagement.services;

import com.example.productmanagement.dto.ProduitDto;
import com.example.productmanagement.entities.Category;
import com.example.productmanagement.entities.Produit;
import com.example.productmanagement.exception.RessourceNotFoundException;
import com.example.productmanagement.repositories.CategoryRepository;
import com.example.productmanagement.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<Produit> getallproducts (String search ,Pageable pageable){
        try {
            Page<Produit> productsPage = null;
            List<Produit> produits = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                productsPage = productRepository.findByNameContainingIgnoreCase(search, pageable);
            } else {
                productsPage = productRepository.findAllByisDeleted(false, pageable);
            }
            return productsPage;
        }catch (Exception ex){
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
            Produit produit=toEntity(produitDto);

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
        // Save the updated product to the database
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

    public Produit toEntity(ProduitDto produitDto) {
        return new Produit(
                produitDto.getId(),
                produitDto.getName(),
                produitDto.getPrice(),
                produitDto.getQuantity(),
                produitDto.getDateCreation(),
                produitDto.getDateModification(),
                new Category(produitDto.getCategory_id())
        );
    }
    private ProduitDto getProduitDto(Produit produit){
        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(produit.getId());
        produitDto.setName(produit.getName());
        produitDto.setPrice(produit.getPrice());
        produitDto.setQuantity(produit.getQuantity());
        produitDto.setCategory_id(produit.getCategory().getId());
        produitDto.setDateCreation(produit.getDatecreation());
        produitDto.setDateModification(produit.getDatemodification());

        return produitDto ;
    }

}
