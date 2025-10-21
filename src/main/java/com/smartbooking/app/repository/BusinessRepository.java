package com.smartbooking.app.repository;

import com.smartbooking.app.entity.Business;
import com.smartbooking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    @Query(value = "select * from Business b where b.owner_id = :ownerId", nativeQuery = true)
    Optional<List<Business>> findByOwnerId(@RequestParam("ownerId") Long ownerId);
}
