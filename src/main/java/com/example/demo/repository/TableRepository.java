package com.example.demo.repository;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {

	TableEntity findByNameTable(String nameTable);

	Page<TableEntity> findByStatus(TableStatus status, Pageable pageable);

	// find table delete = false

	List<TableEntity> findByIsDeleted(boolean deleted);

}
