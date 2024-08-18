package com.techie.ecommerce.repository;

import com.techie.ecommerce.domain.dto.ProductCreation;
import com.techie.ecommerce.domain.model.ProductCreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCreateRepository extends JpaRepository<ProductCreationEntity,Integer> {
}
