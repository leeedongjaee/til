package com.example.til.repository;


import com.example.til.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글 저장 메서드 추가
    Post save(Post post);

    List<Post> findByAuthorId(Long authorId);


}



