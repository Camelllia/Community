package com.doshisha.blog.member.service;

import com.doshisha.blog.member.domain.Member;
import com.doshisha.blog.member.dto.MemberForm;
import com.doshisha.blog.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void test1() {

        //given
        MemberForm memberForm = MemberForm.builder()
                .email("test@test.com")
                .password("1234")
                .username("doshisha")
                .age(33)
                .role("admin")
                .build();

        //when
        memberService.join(memberForm);

        //then
        Member member = memberRepository.findAll().get(0);
        assertEquals(1L, memberRepository.count());
        assertEquals("test@test.com", member.getEmail());
        assertEquals("doshisha", member.getUsername());
        assertEquals(33, member.getAge());
        assertEquals("admin", member.getRole());
    }
}