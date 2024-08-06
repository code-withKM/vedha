package com.ecommerce.vedha.controller;

import com.ecommerce.vedha.entity.User;
import com.ecommerce.vedha.model.ProductResponse;
import com.ecommerce.vedha.model.UserDTO;
import com.ecommerce.vedha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.OK);
    }
}
