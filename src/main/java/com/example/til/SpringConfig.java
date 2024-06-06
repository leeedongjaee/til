package com.example.til;

import com.example.til.repository.MemberRepository;
import com.example.til.repository.PostRepository;
import com.example.til.service.LikeService;
import com.example.til.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    @Bean
    public LikeService likeService() {
        return new LikeService(postRepository, memberRepository);
    }
}
