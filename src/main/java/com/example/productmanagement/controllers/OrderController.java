package com.example.productmanagement.controllers;

import com.example.productmanagement.dto.OrderDto;
import com.example.productmanagement.entities.Orders;
import com.example.productmanagement.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?>getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Page<Orders> orders =orderService.getOrdersById(PageRequest.of(page, size),userId);
        Map<String,Object> response=new HashMap<>();
        response.put("orders",orders.getContent());
        response.put("currentPage",orders.getNumber());
        response.put("totalItems",orders.getTotalElements());
        response.put("totalPages",orders.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllOrders(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Page<Orders> orders =orderService.getAllOrders(PageRequest.of(page, size));
        Map<String,Object> response=new HashMap<>();
        response.put("orders",orders.getContent());
        log.info(" \n ------------------------- \n ");
        log.info("orders {}",orders.getContent());

        response.put("currentPage",orders.getNumber());
        response.put("totalItems",orders.getTotalElements());
        response.put("totalPages",orders.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public Orders insertOrder(@RequestBody OrderDto orderDto ) {

        return orderService.insertOrder(orderDto);
    }
    @PutMapping("/{order_id}")
    public Orders updateOrder(@RequestBody OrderDto orderDto , @PathVariable Long order_id) {
        log.info("order with id {} in updating method", order_id);

        return orderService.updateOrder(order_id);
    }
}
