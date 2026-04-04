package com.example.umcspringbootstudy.comment.repository;

import com.example.umcspringbootstudy.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
