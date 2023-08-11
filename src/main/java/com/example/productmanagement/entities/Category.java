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
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datecreation = new Date();

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datemodification;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(Long id) {
        this.id = id;
    }
}
