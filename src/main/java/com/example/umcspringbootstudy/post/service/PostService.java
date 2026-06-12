package com.example.umcspringbootstudy.post.service;

import com.example.umcspringbootstudy.global.apiPayload.exception.GeneralException;
import com.example.umcspringbootstudy.post.dto.*;
import com.example.umcspringbootstudy.post.exception.code.PostErrorCode;
import com.example.umcspringbootstudy.post.entity.Post;
import com.example.umcspringbootstudy.post.repository.PostRepository;
import com.example.umcspringbootstudy.user.entity.User;
import com.example.umcspringbootstudy.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

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
    public PostListResponseDto getPosts(int page, int size, String sortBy,
                                        String keyword,
                                        LocalDateTime startDate, LocalDateTime endDate) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Post> postPage = postRepository.searchPosts(keyword, startDate, endDate, pageable);

        stopWatch.stop();
        log.info("[JPA] getPosts 실행 시간: {}ms", stopWatch.getTotalTimeMillis());

        return PostListResponseDto.from(postPage);
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPostsQueryDsl(int page, int size, String sortBy,
                                                String keyword,
                                                LocalDateTime startDate, LocalDateTime endDate) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Post> postPage = postRepository.searchPostsQueryDsl(keyword, startDate, endDate, pageable);

        stopWatch.stop();
        log.info("[QueryDSL] getPostsQueryDsl 실행 시간: {}ms", stopWatch.getTotalTimeMillis());

        return PostListResponseDto.from(postPage);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> explainPosts() {
        String sql = "EXPLAIN SELECT * FROM post ORDER BY created_at DESC";
        List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();
        String[] columns = {"id", "select_type", "table", "partitions", "type",
                "possible_keys", "key", "key_len", "ref", "rows", "filtered", "Extra"};

        return rows.stream()
                .map(row -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    for (int i = 0; i < columns.length; i++) {
                        map.put(columns[i], row[i]);
                    }
                    return map;
                })
                .toList();
    }
}
