package com.bookinghotel.repository;

import com.bookinghotel.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE p.deleteFlag = false")
  Page<Product> findAll(Pageable pageable);

  @Query("SELECT p FROM Product p WHERE p.id = ?1 AND p.deleteFlag = false")
  Optional<Product> findById(Long id);

  @Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.deleteFlag = false")
  Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

}
