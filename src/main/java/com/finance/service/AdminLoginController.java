package com.finance.service;

import com.finance.config.CryptoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {

    private static final String ADMIN_HASH = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9";

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("adminId") String adminId,
                               @RequestParam("adminPw") String adminPw,
                               HttpSession session,
                               Model model) {
        
        String hashedPw = CryptoUtils.encryptSHA256(adminPw);
        
        if ("admin".equals(adminId) && ADMIN_HASH.equals(hashedPw)) {
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