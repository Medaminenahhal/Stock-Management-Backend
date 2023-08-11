package com.example.productmanagement.repositories;

import com.example.productmanagement.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Produit, Long> {


    boolean existsByname(String name);

    List<Produit> findAllByisDeleted(boolean b);


}
