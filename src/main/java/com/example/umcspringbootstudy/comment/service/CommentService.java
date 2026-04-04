package com.example.umcspringbootstudy.comment.service;

import com.example.umcspringbootstudy.comment.dto.CommentRequestDto;
import com.example.umcspringbootstudy.comment.dto.CommentResponseDto;
import com.example.umcspringbootstudy.comment.entity.Comment;
import com.example.umcspringbootstudy.comment.exception.code.CommentErrorCode;
import com.example.umcspringbootstudy.comment.repository.CommentRepository;
import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;
import com.example.umcspringbootstudy.post.entity.Post;
import com.example.umcspringbootstudy.post.repository.PostRepository;
import com.example.umcspringbootstudy.user.entity.User;
import com.example.umcspringbootstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GeneralException(CommentErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new GeneralException(CommentErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getPost().getId(),
                savedComment.getUser().getId(),
                savedComment.getContent()
        );
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new GeneralException(CommentErrorCode.COMMENT_UNAUTHORIZED);
        }

        commentRepository.delete(comment);
    }
}
