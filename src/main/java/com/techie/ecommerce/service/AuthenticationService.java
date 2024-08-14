package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;

public interface AuthenticationService {
    String login(String email, String password);
    UserEntity signUp(UserEntity userEntity);
    void logout();
}
