package com.bookinghotel.repository;

import com.bookinghotel.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

  @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
  List<Media> findByRoomId(Long roomId);

}
