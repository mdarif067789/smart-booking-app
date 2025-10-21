package com.smartbooking.app.repository;

import com.smartbooking.app.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    // all providers in one business
    @Query(value = "select * from ServiceProvider " +
            "WHERE business_id = :businessId", nativeQuery = true)
    List<ServiceProvider> findByBusinessId(@Param("businessId") Long businessId);

    // all providers across every business owned by a given owner
    @Query(value = "SELECT sp.* FROM service_provider sp " +
            "JOIN business b ON sp.business_id = b.id " +
            "JOIN user u ON u.id = b.owner_id " +
            "WHERE u.id = :ownerId", nativeQuery = true)
    List<ServiceProvider> findByBusinessOwnerId(@Param("ownerId") Long ownerId);

}
