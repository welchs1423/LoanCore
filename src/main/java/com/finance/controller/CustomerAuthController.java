package com.finance.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerAuthController {

    @GetMapping("/login")
    public String loginForm() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("applicationId") String applicationId, HttpSession session) {
        session.setAttribute("CUSTOMER_APP_ID", applicationId);
        return "redirect:/customer/mypage?id=" + applicationId;
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("CUSTOMER_APP_ID");
        return "redirect:/customer/login";
    }
}