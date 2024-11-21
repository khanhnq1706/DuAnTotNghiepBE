package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.UserEnitty;

public interface UserRepository extends JpaRepository<UserEnitty, UUID> {
    UserEnitty findByUsername(String username);
    Page<UserEnitty> findAll(Specification<UserEnitty> spec, Pageable pageable);
}
