package com.smartbooking.app.repository;

import com.smartbooking.app.entity.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceDetails, Long> {

    @Query(value = "select * from ServiceDetails WHERE s.business_id = :businessId", nativeQuery = true)
    List<ServiceDetails> findByBusinessId(@Param("businessId") Long businessId);

    List<ServiceDetails> findByServiceProviderId(Long providerId);

    @Query(value = "select sd.* from ServiceDetails sd " +
            "JOIN Business b ON sd.business_id = b.id " +
            "JOIN User u ON u.id = b.owner_id " +
            "WHERE u.id= :ownerId", nativeQuery = true)
    List<ServiceDetails> findAllByBusinessOwnerId(@Param("ownerId") Long ownerId);
}
