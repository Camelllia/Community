package com.doshisha.blog.member.response;

import com.doshisha.blog.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * {
 *     "code" : "400"
 *     "message" : "잘못된 요청입니다."
 *     "vaildation" : {
 *          "title" : "값을 입력해주세요"
 *     }
 * }
 */

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> vaildation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addVaildation(String fieldName, String errorMessage) {
        this.vaildation.put(fieldName, errorMessage);
    }
}
