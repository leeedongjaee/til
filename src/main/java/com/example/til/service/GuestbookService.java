package com.example.til.service;

import com.example.til.domain.Guestbook;
import com.example.til.domain.Member;
import com.example.til.repository.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Autowired
    public GuestbookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    public Guestbook addGuestbookEntry(Member member, String message) {
        Guestbook guestbook = new Guestbook();
        guestbook.setMember(member);
        guestbook.setMessage(message);
        guestbook.setCreatedAt(LocalDateTime.now());
        return guestbookRepository.save(guestbook);
    }

    public List<Guestbook> getGuestbookEntries(Long memberId) {
        return guestbookRepository.findByMemberId(memberId);
    }
}