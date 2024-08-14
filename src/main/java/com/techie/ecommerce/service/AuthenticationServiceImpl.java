package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {



    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserEntity signUp(UserEntity userEntity) {
        return null;
    }

    @Override
    public void logout() {

    }
}
