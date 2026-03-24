package com.example.umcspringbootstudy.post.repository;

import com.example.umcspringbootstudy.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
