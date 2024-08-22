package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;

public interface MailService {
    void sendPasswordRestToken(UserEntity userEntity, String token);
}
