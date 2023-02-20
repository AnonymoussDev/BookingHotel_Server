package com.bookinghotel.repository;

import com.bookinghotel.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

  @Query(value = "SELECT * FROM Service s WHERE (:keyword IS NULL OR s.title LIKE CONCAT('%', :keyword, '%')) AND s.deleteFlag = 0",
      countQuery = "SELECT COUNT(*) FROM Service s WHERE (:keyword IS NULL OR s.title LIKE CONCAT('%', :keyword, '%')) AND s.deleteFlag = 0",
      nativeQuery = true)
  Page<Service> findAllByKey(Pageable pageable, String keyword);

  @Query("SELECT s FROM Service s WHERE s.id = ?1 AND s.deleteFlag = false")
  Optional<Service> findById(Long id);

}
