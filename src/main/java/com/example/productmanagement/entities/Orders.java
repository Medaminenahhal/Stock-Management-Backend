package com.example.productmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Double price ;
    private Long quantity;

    private String state;

    private Date dateDeCommande;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private User user;



    public Orders(Long id , Double price , Long quantity, String state,Produit produit, Date dateDeCommande, User user) {
        this.id=id;
        this.price = price;
        this.quantity=quantity;
        this.state=state;
        this.produit=produit;
        this.dateDeCommande=dateDeCommande;
        this.user = user;

    }


}