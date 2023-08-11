package com.example.productmanagement.controllers;

import com.example.productmanagement.dto.ProduitDto;
import com.example.productmanagement.entities.Produit;
import com.example.productmanagement.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produits")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProduitDto> getAllProducts() {
        return productService.getallproducts();
    }

    @GetMapping("/{id}")
    public ProduitDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
    @PostMapping
    public Produit insertproduit(@RequestBody ProduitDto produitDto ) {
        return productService.insertproduct(produitDto);
    }
    @DeleteMapping("/{id}")
    public void deleteproduit(@PathVariable long id) {
        productService.deleteproduct(id);
    }
    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody ProduitDto updatedProduit ) {
        // Set the productId to the updatedProduit based on the ID provided in the path

        return productService.updateProduit(updatedProduit , id);
    }
    @PutMapping("/archiver/{id}")
    public Produit archiverProduit(@PathVariable Long id, @RequestBody Produit updatedProduit) {
        // Set the productId to the updatedProduit based on the ID provided in the path

        return productService.archiverProduit(id ,updatedProduit);
    }
    @PutMapping("/activer/{id}")
    public Produit activerProduit(@PathVariable Long id, @RequestBody Produit updatedProduit) {
        // Set the productId to the updatedProduit based on the ID provided in the path

        return productService.activerProduit(id , updatedProduit);
    }


}
