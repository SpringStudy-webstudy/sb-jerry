package com.example.umcspringbootstudy.post.repository;

import com.example.umcspringbootstudy.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @Query(
            value = "SELECT p FROM Post p JOIN FETCH p.user " +
                    "WHERE (:keyword IS NULL OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
                    "AND (:startDate IS NULL OR p.createdAt >= :startDate) " +
                    "AND (:endDate IS NULL OR p.createdAt <= :endDate)",
            countQuery = "SELECT COUNT(p) FROM Post p " +
                    "WHERE (:keyword IS NULL OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
                    "AND (:startDate IS NULL OR p.createdAt >= :startDate) " +
                    "AND (:endDate IS NULL OR p.createdAt <= :endDate)"
    )
    Page<Post> searchPosts(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    void deleteAllByUserId(Long userId);
}
