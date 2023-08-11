package com.example.productmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryDto {
    private Long id ;
    private String name;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datecreation = new Date();

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm")
    private Date datemodification;
}
