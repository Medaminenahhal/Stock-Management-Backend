package com.example.productmanagement.controllers;

import com.example.productmanagement.entities.User;
import com.example.productmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Page<User> users = userService.getAllUsers(PageRequest.of(page, size));
        Map<String,Object> response=new HashMap<>();
        response.put("users",users.getContent());
        response.put("currentPage",users.getNumber());
        response.put("totalItems",users.getTotalElements());
        response.put("totalPages",users.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        // Set the productId to the updatedProduit based on the ID provided in the path
        return userService.updateUser(id, user);
    }
}
