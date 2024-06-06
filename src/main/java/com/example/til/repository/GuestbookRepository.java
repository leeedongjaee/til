package com.example.til.repository;


import com.example.til.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    List<Guestbook> findByMemberId(Long memberId);
}