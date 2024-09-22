package com.ecommerce.vedha.service;

import com.ecommerce.vedha.entity.User;
import com.ecommerce.vedha.model.LoginDTO;
import com.ecommerce.vedha.model.UserDTO;
import com.ecommerce.vedha.responses.LoginMessage;

public interface UserService {
    User registerUser(User user);

    public User saveUser(UserDTO userDTO);

    public LoginMessage loginUser(LoginDTO loginDTO);
}
