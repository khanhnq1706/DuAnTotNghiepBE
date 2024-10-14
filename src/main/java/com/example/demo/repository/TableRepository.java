package com.example.demo.repository;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.respone.TableResponseDTO;
@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {

	TableEntity findByNameTable(String nameTable);

		   
		   
		  
		Page<TableEntity> findBynameTableContainingOrStatusOrLocationContaining(String nameTable,
				TableStatus status, String location, Pageable pageable);
	


	
}
