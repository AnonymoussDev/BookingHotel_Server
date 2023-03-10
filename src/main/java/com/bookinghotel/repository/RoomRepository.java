package com.bookinghotel.repository;

import com.bookinghotel.constant.RoomType;
import com.bookinghotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  @Query("SELECT r FROM Room r WHERE r.id = ?1 and r.deleteFlag = false")
  Optional<Room> findById(Long id);

  @Query(value = "SELECT * FROM rooms r WHERE (:keyword is null or r.title LIKE CONCAT('%', :keyword, '%')) " +
      "AND (:filter is null or r.type = :filter) AND r.delete_flag = 0",
      countQuery = "SELECT COUNT(*) FROM rooms r WHERE (:keyword is null or r.title LIKE CONCAT('%', :keyword, '%')) " +
          "AND (:filter is null or r.type = :filter) AND r.delete_flag = 0",
      nativeQuery = true)
  Page<Room> findAllByKey(@Param("keyword") String keyword, @Param("filter") String filter, Pageable pageable);

}
