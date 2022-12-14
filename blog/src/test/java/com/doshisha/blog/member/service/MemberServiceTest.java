package com.doshisha.blog.member.service;

import com.doshisha.blog.member.domain.Member;
import com.doshisha.blog.member.dto.LoginForm;
import com.doshisha.blog.member.dto.MemberForm;
import com.doshisha.blog.member.repository.MemberRepository;
import com.doshisha.blog.security.jwt.dto.TokenInfo;
import com.doshisha.blog.security.jwt.provider.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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
                .passwordRepeat("1234")
                .username("name")
                .age(33)
                .build();

        //when
        memberService.join(memberForm);

        //then
        Member member = memberRepository.findAll().get(0);
        assertEquals(1L, memberRepository.count());
        assertEquals("test@test.com", member.getEmail());
        assertEquals("name", member.getUsername());
        assertEquals(33, member.getAge());
    }

    @Test
    @DisplayName("로그인")
    void test2() {

        //given
        MemberForm memberForm = MemberForm.builder()
                .email("test@test.com")
                .password("1234")
                .passwordRepeat("1234")
                .username("name")
                .age(33)
                .build();

        memberService.join(memberForm);

        LoginForm loginForm = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        //when
        TokenInfo jwtToken = memberService.login(loginForm);
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());
        
        //then
        assertEquals(1L, memberRepository.count());
        assertEquals(jwtToken.getGrantType(), "Bearer");
        assertEquals(authentication.getName(), "test@test.com");
        assertTrue(jwtTokenProvider.validateToken(jwtToken.getAccessToken()));
        assertTrue(jwtTokenProvider.validateToken(jwtToken.getRefreshToken()));
    }
}