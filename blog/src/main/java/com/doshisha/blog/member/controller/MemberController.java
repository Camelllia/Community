package com.doshisha.blog.member.controller;

import com.doshisha.blog.member.dto.MemberForm;
import com.doshisha.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login/form")
    public String form(Model model) {
        model.addAttribute("userForm", new MemberForm());
        return "user/login/register";
    }

    @PostMapping("/login/form")
    public String join(@RequestBody @Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "user/login/register";
        }
        memberService.join(form);
        return "redirect:/";
    }
}