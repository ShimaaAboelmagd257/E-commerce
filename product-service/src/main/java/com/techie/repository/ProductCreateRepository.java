package com.techie.repository;

import com.techie.domain.ProductCreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCreateRepository extends JpaRepository<ProductCreationEntity,Integer> {
}
