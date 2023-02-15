package com.bookinghotel.repository;

import com.bookinghotel.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("SELECT p FROM Post p WHERE p.id = ?1 AND p.deleteFlag = false")
  Optional<Post> findById(Long postId);

  @Query(value = "SELECT * FROM posts p WHERE p.delete_flag = 0",
      countQuery = "SELECT COUNT(*) FROM sales p WHERE p.delete_flag = 0",
      nativeQuery = true)
  Page<Post> findAll(Pageable pageable);
}
