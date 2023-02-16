package com.bookinghotel.repository;

import com.bookinghotel.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

  //room
  @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
  List<Media> findByRoomId(Long roomId);

  @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m.deleteFlag = false")
  Set<Media> findByRoomToSet(Long roomId);

  @Query("SELECT m FROM Media m WHERE m.room.id = ?1 AND m NOT IN ?2 AND m.deleteFlag = false")
  List<Media> findByRoomIdAndNotInMedia(Long roomId, List<Media> list);

  //post
  @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.deleteFlag = false")
  List<Media> findByPostId(Long postId);

  @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m.deleteFlag = false")
  Set<Media> findByPostToSet(Long postId);

  @Query("SELECT m FROM Media m WHERE m.post.id = ?1 AND m NOT IN ?2 AND m.deleteFlag = false")
  List<Media> findByPostIdAndNotInMedia(Long postId, List<Media> list);

  //product
  @Query("SELECT m FROM Media m WHERE m.product.id = ?1 AND m.deleteFlag = false")
  List<Media> findByProductId(Long productId);

  @Query("SELECT m FROM Media m WHERE m.product.id = ?1 AND m.deleteFlag = false")
  Set<Media> findByProductToSet(Long productId);

  @Query("SELECT m FROM Media m WHERE m.product.id = ?1 AND m NOT IN ?2 AND m.deleteFlag = false")
  List<Media> findByProductIdAndNotInMedia(Long productId, List<Media> list);

}
