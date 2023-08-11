package com.example.productmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
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
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datecreation=new Date();
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datemodification;
    @ManyToOne
    private Category category;



    public Produit(Long id,String name, Double price, Category category) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.category = category;

    }
}
