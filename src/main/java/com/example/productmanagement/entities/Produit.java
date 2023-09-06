package com.example.productmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private Double price ;
    private boolean isDeleted;
    private Long quantity;

    private Date datecreation=new Date();

    private Date datemodification;
    @ManyToOne
    private Category category;



    public Produit(Long id,String name,Double price , Long quantity, Date datecreation,Date datemodification,Category category) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
        this.datecreation=datecreation;
        this.datemodification=datemodification;
        this.category = category;

    }


    public Produit(Long productId) {
        this.id=productId;
    }
}
