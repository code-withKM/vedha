package com.ecommerce.vedha.service;

import com.ecommerce.vedha.entity.Role;
import com.ecommerce.vedha.entity.User;
import com.ecommerce.vedha.exception.UserExitsException;
import com.ecommerce.vedha.repository.RoleRepository;
import com.ecommerce.vedha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
