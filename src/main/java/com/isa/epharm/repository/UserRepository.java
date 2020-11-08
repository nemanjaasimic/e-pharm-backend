package com.isa.epharm.repository;

import com.isa.epharm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE username = :usernameOrEmail OR email = :usernameOrEmail ")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}
