package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 테스트를 위해 "admin123"을 실시간으로 BCrypt 암호화 (실무에서는 DB에 저장된 해시값을 가져옴)
    private String getAdminPwHash() {
        return passwordEncoder.encode("admin123");
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("adminId") String adminId,
                          @RequestParam("adminPw") String adminPw,
                          HttpSession session) {
        
        // 1. 아이디 확인 & 2. 사용자가 입력한 평문 비밀번호와 해시값을 안전하게 비교 (matches)
        if ("admin".equals(adminId) && passwordEncoder.matches(adminPw, getAdminPwHash())) {
            session.setAttribute("adminId", adminId);
            session.setAttribute("adminLogined", true); // 인터셉터 통과용
            return "redirect:/";
        }
        
        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}