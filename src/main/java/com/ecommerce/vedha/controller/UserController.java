package com.ecommerce.vedha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.vedha.entity.User;
import com.ecommerce.vedha.exception.UserAlreadyExistsException;
import com.ecommerce.vedha.model.LoginDTO;
import com.ecommerce.vedha.model.UserDTO;
import com.ecommerce.vedha.responses.LoginMessage;
import com.ecommerce.vedha.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") 
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO.getUsername() == null || userDTO.getUsername().isBlank()) {
                return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
            }
            if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
                return new ResponseEntity<>("Password is required", HttpStatus.BAD_REQUEST);
            }
            if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
            }
            User savedUser = userService.saveUser(userDTO);
            return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while saving the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginMessage loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }


}
