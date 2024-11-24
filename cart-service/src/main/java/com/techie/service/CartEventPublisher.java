package com.techie.service;

import com.techie.domain.ProductRequestEvent;
import com.techie.mappers.ProductResponceMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(CartEventPublisher.class);

    private ProductResponceMapperImpl mapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.product-request}")
    private String productRequestTopic;

    public void requestProductInfo(Long cartId, Integer productId){
        ProductRequestEvent event = new ProductRequestEvent(UUID.randomUUID().toString(),cartId,productId);
        kafkaTemplate.send(productRequestTopic,event);
        logger.info("---------------Sending productId to topic {}: {}-----------------", productRequestTopic, productId);

    }
}
