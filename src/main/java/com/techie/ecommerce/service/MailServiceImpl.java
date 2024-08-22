package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
    @Override
    public void sendPasswordRestToken(UserEntity userEntity, String token) {

    }
}
