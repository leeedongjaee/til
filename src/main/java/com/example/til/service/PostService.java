package com.example.til.service;
import com.example.til.domain.Member;
import com.example.til.domain.Post;
import com.example.til.repository.MemberRepository;
import com.example.til.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;
    public void createPost(Post post) {


        postRepository.save(post);
    }
    public List<String> saveImages(List<MultipartFile> images) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileName = saveImage(image);
            String fileUrl = "/images/" + fileName; // URL 경로
            imageUrls.add(fileUrl);
        }
        return imageUrls;
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        File file = new File(filePath.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        image.transferTo(filePath.toFile());
        return fileName;
    }
    public void deleteImages(List<String> imageUrls) {
        Path rootLocation = Paths.get(uploadDir);
        imageUrls.forEach(imageUrl -> {
            try {
                Files.deleteIfExists(rootLocation.resolve(imageUrl.replace("/upload-dir/", "")));
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file " + imageUrl, e);
            }
        });
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }


    public void deletePost(Long postId, String nickname, String password) {
        Optional<Member> author = memberRepository.findByNickname(nickname);
        if (author.isPresent() && author.get().getPassword1().equals(password)) {
            postRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void updatePost(Long postId, String title, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }


    public List<Post> findPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }


    @Transactional
    public void increaseViewCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }




}
