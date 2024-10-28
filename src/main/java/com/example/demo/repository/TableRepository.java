package com.example.demo.repository;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {

	TableEntity findByNameTable(String nameTable);

	Page<TableEntity> findByStatus(TableStatus status, Pageable pageable);

	List<TableEntity> findByIsLocked(boolean locked);

	@Query("SELECT t FROM TableEntity t WHERE t.linkImageQr IS NOT NULL")
	List<TableEntity> findTablesWithQrCode();

	@Query(value = "SELECT * FROM table_entity WHERE id_area = :idArea ORDER BY CAST(SUBSTRING(name_table, 5) AS UNSIGNED) ASC", nativeQuery = true)
	Page<TableEntity> findAllSortedByNameTableASC(@Param("idArea") Integer idArea, Pageable pageable);

	@Query(value = "SELECT * FROM table_entity WHERE id_area = :idArea ORDER BY CAST(SUBSTRING(name_table, 5) AS UNSIGNED) DESC", nativeQuery = true)
	Page<TableEntity> findAllSortedByNameTableDESC(@Param("idArea") Integer idArea, Pageable pageable);

	// Tìm kiếm theo nameTable, status, idArea với phân trang
	@Query("SELECT t FROM TableEntity t WHERE " +
			"(:nameTable IS NULL OR t.nameTable LIKE %:nameTable%) AND " +
			"(:status IS NULL OR t.status = :status) AND " +
			"(:idArea IS NULL OR t.area.idArea = :idArea)")
	Page<TableEntity> findByFilters(@Param("nameTable") String nameTable,
			@Param("status") TableStatus status,
			@Param("idArea") Integer idArea,
			Pageable pageable);
}
