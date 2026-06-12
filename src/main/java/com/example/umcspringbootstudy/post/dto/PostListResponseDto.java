package com.example.umcspringbootstudy.post.dto;

import com.example.umcspringbootstudy.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResponseDto {
    private List<PostDetailResponseDto> posts; // 페이지 데이터 목록

    private Integer currentPage; // 현재 페이지 번호 (0부터 시작)
    private Integer totalPages; // 전체 페이지 수
    private Long totalElements; // 전체 데이터 수
    private Integer listSize; // 이번 페이지에 실제 담긴 데이터 수
    private Boolean isFirst; // 첫번째 페이지 여부
    private Boolean isLast; // 마지막 페이지 여부
    private Boolean hasNext; // 다음 페이지 존재 여부
    private Boolean hasPrevious; // 이전 페이지 존재 여부

    public static PostListResponseDto from(Page<Post> page) {
        List<PostDetailResponseDto> posts =  page.getContent().stream()
                .map(post -> new PostDetailResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUser().getNickname(),
                        post.getCreatedAt()
                )).toList();

        return new PostListResponseDto(
                posts,
                page.getNumber(),           // currentPage
                page.getTotalPages(),        // totalPages
                page.getTotalElements(),     // totalElements
                page.getNumberOfElements(), // listSize
                page.isFirst(),             // isFirst
                page.isLast(),              // isLast
                page.hasNext(),             // hasNext
                page.hasPrevious()          // hasPrevious
        );
    }
}
