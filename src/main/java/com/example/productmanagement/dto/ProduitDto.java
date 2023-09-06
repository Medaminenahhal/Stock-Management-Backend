package com.example.productmanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDto {

    private Long id;
    private String name;
    private Double Price ;
    private Long quantity;
    private Long category_id;
    private Date dateCreation=new Date();
    private Date dateModification;

}
