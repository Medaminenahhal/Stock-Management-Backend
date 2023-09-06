package com.example.productmanagement.dto;

import lombok.Data;
import java.util.Date;
@Data
public class OrderDto {


        private Long id;
        private Double Price ;
        private Long quantity;
        private String state;
        private Long user_id;
        private Long product_id;
        private Date dateDeCommande=new Date();



}
