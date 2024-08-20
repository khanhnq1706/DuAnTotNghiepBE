package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FirstTestDb;

public interface FirstTestDbRepository extends JpaRepository<FirstTestDb, Integer> {

}
