package com.smartbooking.app.repository;

import com.smartbooking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.* from User  u WHERE u.email = :email" , nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    List<User> findByBusinessId(Long businessId);
}
