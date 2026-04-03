package com.finance.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;

@Controller
public class LoanWebController {

	@Autowired
	private LoanReviewService reviewService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("totalCount", reviewService.getTotalCount());
		model.addAttribute("applications", reviewService.getAllApplications());
		return "index";
	}

	@GetMapping("/apply")
	public String applyForm() {
		return "apply";
	}

	@PostMapping("/submit-loan")
	public String submitLoan(@RequestParam("customerId") String customerId, @RequestParam("amount") BigDecimal amount,
			@RequestParam("address") String address,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) { // 파일 파라미터 추가

		LoanApplication app = new LoanApplication();
		app.setCustomerId(customerId);
		app.setAmount(amount);
		app.setAddress(address);

		// === 신규 파일 업로드 로직 시작 ===
		if (uploadFile != null && !uploadFile.isEmpty()) {
			// upload.properties에 정의된 실제 경로 활용
			String uploadDir = "C:/upload/loan_docs/";
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs(); // 폴더가 없으면 자동 생성
			}

			// 파일명 중복 방지를 위한 UUID 적용
			String originalName = uploadFile.getOriginalFilename();
			String savedName = UUID.randomUUID().toString() + "_" + originalName;

			try {
				// 실제 지정된 폴더로 파일 복사
				uploadFile.transferTo(new File(uploadDir + savedName));
				// DB 저장을 위해 DTO에 파일명 세팅
				app.setFileName(savedName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// === 파일 업로드 로직 끝 ===

		reviewService.applyLoan(app);
		return "redirect:/";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		LoanApplication app = reviewService.getApplicationById(id);
		model.addAttribute("app", app);
		return "detail";
	}

	@PostMapping("/approve")
	public String approveLoan(@RequestParam("applicationId") String applicationId) {
		LoanApplication app = reviewService.getApplicationById(applicationId);
		if (app != null) {
			reviewService.reviewLoan(app);
		}
		return "redirect:/";
	}

	@GetMapping("/api/memos/{appId}")
	@ResponseBody
	public List<LoanMemo> getMemos(@PathVariable("appId") String appId) {
		return reviewService.getMemosByAppId(appId);
	}

	/*
	 * @PostMapping("/api/memos")
	 * 
	 * @ResponseBody public String addMemo(@RequestBody LoanMemo memo) {
	 * reviewService.addMemo(memo); return "success"; }
	 */

	@GetMapping("/audit")
	public String auditLogDashboard(Model model) {
		model.addAttribute("auditLogs", reviewService.getRecentAuditLogs());
		return "audit";
	}
}