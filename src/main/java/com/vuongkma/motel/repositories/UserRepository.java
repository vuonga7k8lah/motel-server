package com.vuongkma.motel.repositories;

import com.vuongkma.motel.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {
    Optional<User> findByEmail(String email);
}
