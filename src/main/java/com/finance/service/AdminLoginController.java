package com.finance.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("adminId") String adminId,
                               @RequestParam("adminPw") String adminPw,
                               HttpSession session,
                               Model model) {
        
        if ("admin".equals(adminId) && "admin123".equals(adminPw)) {
            session.setAttribute("adminLogined", true);
            return "redirect:/list";
        } else {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}