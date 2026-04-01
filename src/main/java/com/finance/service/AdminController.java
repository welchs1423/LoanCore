package com.finance.service;

import com.finance.util.CryptoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final String ADMIN_ID = "admin";
    // "admin123"을 SHA-256으로 해싱한 값 (하드코딩 매칭용)
    private final String ADMIN_PW_HASH = CryptoUtil.encryptSHA256("admin123");

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("adminId") String adminId,
                          @RequestParam("adminPw") String adminPw,
                          HttpSession session) {
        
        // 입력받은 패스워드를 해싱하여 저장된 해시값과 비교
        if (ADMIN_ID.equals(adminId) && ADMIN_PW_HASH.equals(CryptoUtil.encryptSHA256(adminPw))) {
            session.setAttribute("adminId", adminId);
            return "redirect:/"; // 성공 시 메인 대시보드로
        }
        return "redirect:/login?error=true"; // 실패 시 다시 로그인 창으로
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 파기
        return "redirect:/login";
    }
}