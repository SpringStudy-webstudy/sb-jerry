package com.example.umcspringbootstudy.post.repository;

import com.example.umcspringbootstudy.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostRepositoryCustom {

    Page<Post> searchPostsQueryDsl(
            String keyword,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
