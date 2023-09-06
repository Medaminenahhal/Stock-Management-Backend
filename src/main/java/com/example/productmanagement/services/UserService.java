package com.example.productmanagement.services;

import com.example.productmanagement.entities.User;
import com.example.productmanagement.exception.RessourceNotFoundException;
import com.example.productmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository ;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Page<User> getAllUsers (Pageable pageable){
        try{
            return userRepository.findAll(pageable);}
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch users from the database.");
        }
    }
    public User updateUser( Long id , User user) {
        // Check if the User with the given ID exists
        try{
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null)
                throw new RessourceNotFoundException("User with ID " + id + " not found.");

            // Update the existing user with the new values
           existingUser.setRole(user.getRole());

           // Save the updated user to the database
            return userRepository.save(existingUser);}
        catch (Exception ex){
            throw new RuntimeException("error updating");
        }
    }

}
