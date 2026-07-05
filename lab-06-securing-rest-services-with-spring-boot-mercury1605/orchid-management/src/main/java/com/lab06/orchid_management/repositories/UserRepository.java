package com.lab06.orchid_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab06.orchid_management.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA tự động tạo câu query: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}