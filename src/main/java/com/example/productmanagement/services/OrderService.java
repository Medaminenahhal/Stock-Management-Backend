package com.example.productmanagement.services;

import com.example.productmanagement.dto.OrderDto;
import com.example.productmanagement.dto.ProduitDto;
import com.example.productmanagement.entities.Orders;
import com.example.productmanagement.entities.Produit;
import com.example.productmanagement.entities.User;
import com.example.productmanagement.repositories.OrderRepository;
import com.example.productmanagement.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public OrderService( OrderRepository orderRepository,ProductService productService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productService= productService;
        this.productRepository=productRepository;


    }

    public Page<Orders> getAllOrders (Pageable pageable){
        try{
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "dateDeCommande"));
            Page<Orders> all = orderRepository.findAll(pageable);
            log.info("orders {}",all.getContent());
            return all;}
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch orders from the database.");
        }
    }
    public Page<Orders> getOrdersById (Pageable pageable,Long userId){
        try{
            return orderRepository.findByUserId(userId, pageable);}
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch orders from the database.");
        }
    }
    public Orders insertOrder (OrderDto orderDto){
        try {
            log.info("orderDto:{}", orderDto);
             Orders orders;

             ProduitDto produitDto = productService.getProduct(orderDto.getProduct_id());
             Produit produit=productService.toEntity(produitDto);
             orders=toEntity(orderDto);
             log.info("orders:{}", orders);
             orders.setProduit(produit);
             orders.setPrice((orders.getQuantity())*(orders.getProduit().getPrice()));



                 long quantity = produitDto.getQuantity()- orders.getQuantity();

                 if(quantity<0){
                     throw new RuntimeException("Error inserting Order ");
                 }
                 return orderRepository.save(orders);




            // Check if a product with the same name already exists in the database.
            }
        catch(Exception ex){
            ex.printStackTrace(); // Just an example, you should use a proper logging mechanism.
            throw new RuntimeException("Error inserting Order " + ex.getMessage());
        }
    }
    public Orders updateOrder(Long id){

        Orders order = orderRepository.findById(id).orElse(null);
        assert order != null;
        order.setState("Validated");
        Produit produit;
        produit=productRepository.findById(order.getProduit().getId()).orElseThrow();
        log.info("order in updateOrder {} ", order);

        produit.setQuantity((produit.getQuantity())-(order.getQuantity()));
        productRepository.save(produit);
        log.info("produit in updateOrder {} ", produit);

        order.setState("Validated");

        return orderRepository.save(order);
    }

    private Orders toEntity (OrderDto orderDto) {

        Orders orders = new Orders(
                orderDto.getId(),
                orderDto.getPrice(),
                orderDto.getQuantity(),
                orderDto.getState(),
                new Produit(orderDto.getProduct_id()),
                orderDto.getDateDeCommande(),
                new User(orderDto.getUser_id()));


        log.info("Orders to entity{}:",orders.getState());
        return orders;
    }


}
