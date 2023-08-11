package com.example.productmanagement.dto;

import com.example.productmanagement.entities.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data

public class ProduitDto {
    private Long id;
    private String name;
    private Double Price ;
    private Long category_id;

}
