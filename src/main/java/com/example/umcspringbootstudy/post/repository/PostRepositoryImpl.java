package com.example.umcspringbootstudy.post.repository;

import com.example.umcspringbootstudy.post.entity.Post;
import com.example.umcspringbootstudy.post.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;

    @Override
    public Page<Post> searchPostsQueryDsl(String keyword, LocalDateTime startDate,
                                          LocalDateTime endDate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            builder.and(post.title.contains(keyword)
                    .or(post.content.contains(keyword)));
        }
        if (startDate != null) {
            builder.and(post.createdAt.goe(startDate));
        }
        if (endDate != null) {
            builder.and(post.createdAt.loe(endDate));
        }

        List<Post> posts = queryFactory
                .selectFrom(post)
                .join(post.user).fetchJoin()
                .where(builder)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(posts, pageable, total);
    }
}
