package com.bookinghotel.repository;

import com.bookinghotel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT c FROM Category c WHERE c.deleteFlag = false")
  List<Category> findAll();

  @Query("SELECT c FROM Category c WHERE c.id = ?1 AND c.deleteFlag = false")
  Optional<Category> findById(Long id);

}
