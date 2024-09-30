package com.techie.ecommerce.repository;

import com.techie.ecommerce.domain.model.shippo.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity,Long> {
    Optional<ShipmentEntity> findByTrackingNumber(String trackingNumber );
}
