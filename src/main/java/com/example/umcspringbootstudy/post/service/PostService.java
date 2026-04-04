package com.example.umcspringbootstudy.post.service;

import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;
import com.example.umcspringbootstudy.post.dto.PostDetailResponseDto;
import com.example.umcspringbootstudy.post.exception.code.PostErrorCode;
import com.example.umcspringbootstudy.post.dto.PostRequestDto;
import com.example.umcspringbootstudy.post.dto.PostResponseDto;
import com.example.umcspringbootstudy.post.dto.PostUpdateRequestDto;
import com.example.umcspringbootstudy.post.entity.Post;
import com.example.umcspringbootstudy.post.repository.PostRepository;
import com.example.umcspringbootstudy.user.entity.User;
import com.example.umcspringbootstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GeneralException(PostErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getUser().getId(),
                savedPost.getTitle(),
                savedPost.getContent()
        );
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

        return new PostDetailResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt()
        );
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new GeneralException(PostErrorCode.POST_UNAUTHORIZED);
        }

        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, Long userId, PostUpdateRequestDto request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new GeneralException(PostErrorCode.POST_UNAUTHORIZED);
        }

        post.update(request.getTitle(), request.getContent());

        return new PostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContent()
        );
    }

    @Transactional(readOnly = true)
    public List<PostDetailResponseDto> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> new PostDetailResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUser().getNickname(),
                        post.getCreatedAt()
                ))
                .toList();
    }
}
