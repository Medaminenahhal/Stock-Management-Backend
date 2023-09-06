package com.example.productmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id ;

    private String name;

    private boolean isDeleted = false;

    private Date datecreation = new Date();


    private Date datemodification;

    public Category(Long id, String name, Date datecreation, Date datemodification) {
        this.id = id;
        this.name = name;
        this.datecreation = datecreation;
        this.datemodification = datemodification;
    }
    public Category(Long id) {
        this.id = id;
    }
}
