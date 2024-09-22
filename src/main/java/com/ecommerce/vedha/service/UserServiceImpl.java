package com.ecommerce.vedha.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.vedha.entity.Role;
import com.ecommerce.vedha.entity.User;
import com.ecommerce.vedha.exception.UserAlreadyExistsException;
import com.ecommerce.vedha.exception.UserExitsException;
import com.ecommerce.vedha.model.LoginDTO;
import com.ecommerce.vedha.model.UserDTO;
import com.ecommerce.vedha.repository.RoleRepository;
import com.ecommerce.vedha.repository.UserRepository;
import com.ecommerce.vedha.responses.LoginMessage;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserExitsException("User already exists");
        }

        if (user.getRole() == null) {
            Role defaultRole = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRole(defaultRole);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + userDTO.getEmail());
        }

        Role defaultRole = roleRepository.findById(2)
        .orElseThrow(() -> new RuntimeException("Default Role not found"));

        String password = passwordEncoder.encode(userDTO.getPassword());

        // Convert UserDTO to User entity
        User user;
        user = User.builder()
                .username(userDTO.getUsername())
                .password(password) 
                .email(userDTO.getEmail())
                .role(defaultRole)
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginMessage loginUser(LoginDTO loginDTO) {
        // String msg = "";
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());
    
        if (userOptional.isEmpty()) { // If user is not found
            return new LoginMessage("Email not exists", false);
        }
    
        User user = userOptional.get();
        
        String password = loginDTO.getPassword();
        String encodedPassword = user.getPassword();
        boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
        if (isPwdRight) {
            User userCheck = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
            if (userCheck != null) {
                return new LoginMessage("Login Success", true);
            } else {
                return new LoginMessage("Login Failed", false);
            }
        } else {
            return new LoginMessage("Password Not Match", false);
        }
             
    }

}
