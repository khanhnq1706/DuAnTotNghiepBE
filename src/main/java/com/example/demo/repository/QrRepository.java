package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.QrEntity;
import com.example.demo.entity.TableEntity;
import java.util.List;

@Repository
public interface QrRepository extends JpaRepository<QrEntity, Integer> {

	QrEntity findByTableEntity(TableEntity tableEntity);

	void deleteByTableEntity(TableEntity tableEntity);

}
