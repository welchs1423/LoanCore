# 🏦 LoanCore: 엔터프라이즈 여신(대출) 관리 시스템

금융권의 핵심 업무인 여신 프로세스를 모사하여, 신청부터 심사, 실행(송금), 사후 관리 및 리스크 탐지까지 전 과정을 처리하는 **Spring 기반 백엔드 시스템**입니다.

---

## 🛠️ Tech Stack

* **Backend:** Java 11, Spring MVC 5.x, MyBatis 3.5, AspectJ (AOP), Spring Cache
* **Infrastructure:** **Redis (Distributed Lock & Cache)**, Docker Compose, JMeter (Performance Test)
* **Frontend:** JSP (JSTL), Bootstrap 5, JavaScript (Fetch API), Chart.js 4.x
* **Database:** H2 Database, Spring JDBC, HikariCP
* **Security:** **BCrypt & SHA-256**, Session Interceptor, **RBAC (Role-Based Access Control)**, **IP Whitelisting**
* **Libraries:** Apache POI, Zxing 3.5, **JavaMailSender**, Hibernate Validator, Log4jdbc

---

## 🚀 주요 구현 기능

### 1. 고도화된 여신 비즈니스 로직
* **대출 실행 및 상환 관리:** 대출 승인 후 실제 **가상계좌(Virtual Account)** 발급 및 송금 로직 모사, 회차별 **상환 스케줄 DB 영구 적재** 구현.
* **금융 컴플라이언스 준수:** 법적 권리인 **금리인하요구권** 심사 프로세스 및 **중도상환수수료**의 정밀한 계산(소수점 오차 방지) 로직 구현.
* **특수 채권 관리:** 개인회생/파산 접수 시 **이자 부과 자동 정지** 및 채권 상태 변경(BANKRUPT) 처리 로직 구현.

### 2. 엔터프라이즈급 보안 및 리스크 관리
* **이상거래 탐지(FDS):** 인가되지 않은 IP 접근 차단(**IP Whitelist**) 및 이상 상환 패턴 매칭을 통한 보안 강화.
* **접근 제어(RBAC):** 관리자 등급별(SUPER_ADMIN, MANAGER) API 접근 권한 분리 및 **인터셉터 기반 세션 보안** 고도화.
* **감사 로그(Audit Trail):** 민감한 원장 데이터 변경 시 '변경 전/후' 값을 기록하여 데이터 추적성 확보.
* **데이터 비식별화:** PrivacyMasking 기능을 통해 로그 및 UI상에서 **주민번호/연락처 마스킹** 처리.

### 3. 시스템 안정성 및 성능 최적화
* **분산 환경 동시성 제어:** **Redis Distributed Lock**을 활용하여 동일 채권에 대한 중복 상환 등 동시성 이슈(Double-spending) 원천 차단.
* **전역 예외 처리 표준화:** **`@ControllerAdvice`와 공통 응답 DTO(`ApiResponse`)**를 구축하여 API/View 요청별 맞춤형 에러 응답 체계 단일화.
* **대규모 트래픽 대응:** **JMeter**를 활용한 성능 부하 테스트 환경 구축 및 **Circuit Breaker** 로직을 통한 외부 API 장애 전이 방지.
* **비동기 알림 체계:** 메일 발송 및 알림톡 기능을 비동기/큐잉 방식으로 설계하여 메인 비즈니스 로직의 응답 속도 유지.

### 4. 데이터 거버넌스 및 운영 도구
* **배치 모니터링:** `@Scheduled` 기반 이자 결산/자동이체 배치의 성공/실패 여부를 **Batch Log** 테이블에 기록 및 추적.
* **데이터 파기 정책:** 개인정보 보호법 준수를 위한 보유 기간 만료 데이터 자동 삭제/분리 보관 배치 구현.
* **다양한 데이터 추출:** 관리자용 원장 내역 **Excel/CSV 다운로드** 및 국세청 제출용 연간 이자 상환 증빙 데이터 집계 기능.

---

## 📈 설계 및 아키텍처 포인트

* **정합성 우선:** 금융 시스템 특성에 맞춰 모든 원장 변경은 `@Transactional` 하에 원자성을 보장하도록 설계.
* **확장성 고려:** 인터페이스 기반의 서비스 설계와 공통 응답 규격 적용으로 향후 모바일 앱이나 제휴사 연동이 용이한 구조 확보.

---

### 📅 개발 진행 내역

* **2026-04-04**
  - [Feat] PushMessageService 구현으로 FCM 기반 푸시 알림 발송 모의 로직 및 발송 이력 관리 기능 추가
  - [DB] 푸시 발송 이력 추적을 위한 PUSH_HISTORY 테이블 스키마 DDL 작성
  - [Test] 사용자별 푸시 메시지 생성 및 DB 적재 로직 JUnit 단위 테스트 수행
  - [Refactor] ApiResponse DTO 및 GlobalExceptionHandler 구축으로 시스템 전역 공통 응답 규격 및 예외 처리 체계 표준화
  - [Feat] DocumentManagementService 구현으로 대출 ID별 증빙 서류 메타데이터 DB 매핑 및 통합 관리 로직 적용
  - [Security] MaintenanceInterceptor 도입을 통한 은행 공동망 점검 시간대 API 접근 자동 차단 기능 구현
  - [DB] 대출 관련 증빙 서류 메타데이터 보관을 위한 LOAN_DOCUMENTS 테이블 스키마 DDL 추가
  - [Test] 전역 예외 처리기 및 파일 메타 등록 로직 JUnit 단위 테스트 케이스 작성
  - [Feat] CsvExportService 구현으로 백오피스 관리자용 대출 원장 데이터 CSV 추출 로직 적용
  - [Feat] EmailSenderService 추가를 통한 JavaMailSender 기반 전자 약정서 및 안내문 비동기 발송 연동
  - [Feat] DistributedLockService 구축으로 Redis 기반 동시성 제어(Double-spending 방지) 분산 락 기능 적용
  - [Feat] TcpSocketClient 도입을 통한 대외기관 레거시 시스템 통신용 TCP/IP 소켓 모듈 구현
  - [Test] CSV 추출, 이메일 발송, 분산 락, 소켓 클라이언트 통신 단위 테스트 케이스 추가
  - [Feat] BatchMonitoringService 구현으로 주요 스케줄러(이자, 자동이체 등) 실행 결과 및 에러 DB 로깅 추가
  - [Security] RoleBasedAuthInterceptor 세분화를 통해 관리자 등급(SUPER_ADMIN, MANAGER) 기반 API 접근 제어(RBAC) 적용
  - [Security] IpWhitelistInterceptor 도입으로 외부 웹훅(Webhook) 호출 시 인가된 IP만 허용하는 네트워크 보안 로직 추가
  - [Feat] TaxInvoiceService 구축을 통한 기업 대출 수납용 전자세금계산서 발급 번호 모의 생성 로직 구현
  - [Test] 배치 로깅, 권한 제어, IP 화이트리스트, 세금계산서 발급 단위 테스트 케이스 추가
  - [Feat] RateReductionService 구현으로 신용점수 개선 시 금리인하요구권 심사 및 승인 로직 적용
  - [Feat] EarlyRepaymentService를 활용한 대출 잔여 일수 비례 중도상환수수료(페널티) 산출 기능 구축
  - [Feat] CertificateService 도입으로 금융기관 제출용 대출잔액증명서 고유 발급번호 생성 처리
  - [Feat] OverpaymentBatchService 스케줄러를 통한 완제 고객의 과오납금(미환급금) 자동 스캔 로직 추가
  - [Test] 금리인하 심사, 중도상환수수료 산출, 증명서 발급, 과오납 스캔 단위 테스트 케이스 작성
  - [Feat] LoanLiquidationService 구현으로 대출금 전액 상환 시 완제 처리 및 고유 UUID 생성 로직 적용
  - [Feat] TaxDataService 구축을 통한 연간 이자 상환 총액 집계 및 증빙 데이터 추출 기능 추가
  - [Feat] FdsPatternService 도입으로 이상 IP 및 이상 상환 시도 패턴 매칭 FDS 로직 구현
  - [Config] I18nConfig 설정을 통한 시스템 전역 다국어 지원(Internationalization) 기초 환경 구축
  - [Test] 여신 완제 처리, 연말정산 집계, FDS 패턴 탐지 로직 JUnit 단위 테스트 케이스 작성
  - [Feat] DefaultPredictionService 구현으로 신용도 및 연체 기반 부도 확률(PD) 모의 산출 엔진 구축
  - [Feat] NotificationQueueService 도입을 통한 알림 발송 비동기 처리를 위한 큐잉(Queueing) 로직 적용
  - [Feat] DataRetentionBatchService 구축으로 개인정보 보호법 준수를 위한 만료 데이터 파기 배치 연동
  - [Feat] HealthCheckController 구현으로 서비스 가용성 및 인프라 상태 모니터링 엔드포인트 추가
  - [Test] 부도 확률 계산, 알림 큐 처리 로직 JUnit 단위 테스트 케이스 작성
  - [Feat] AutoDebitBatchService 구축을 통한 정기 결제일 CMS 자동이체 출금 청구 배치 스케줄러 연동
  - [Security] PasswordSecurityService를 활용한 단방향 해시 암호화 및 5회 실패 시 계정 잠금 처리
  - [Security] RateLimiterService 도입으로 외부 API 트래픽 과부하 방지용 호출량 제한 로직 추가
  - [Infra] CloudStorageService를 통한 AWS S3 객체 스토리지 증빙 서류 업로드 모의 연동
  - [Test] 자동이체 배치, 암호화/계정잠금, API Rate Limit, S3 업로드 단위 테스트 케이스 작성
  - [UI/UX] Bootstrap 기반 관리자 대출 원장 상세 조회 화면(AdminLoanController, loanDetail.jsp) 구축
  - [Infra] 대규모 트래픽 대비 JMeter 성능 부하 테스트 환경(LoanCore_TestPlan.jmx) 및 실행 배치 파일 세팅
  - [Feat] VirtualAccountService 구현으로 상환용 고객 고유 가상계좌 발급 및 매핑 로직 추가
  - [Feat] RehabilitationService 연동을 통한 개인회생/파산(BANKRUPT) 접수 및 이자 정지 처리
  - [Feat] CollectionActivityService를 활용한 연체 채권 추심 활동(전화, 방문 등) 이력 DB 누적 기능 구축
  - [DB] 가상계좌(VIRTUAL_ACCOUNT) 및 추심 이력(COLLECTION_ACTIVITY) 테이블 스키마 DDL 추가
  - [Test] 가상계좌 발급, 파산 접수, 추심 로깅 단위 테스트 케이스 추가
  - [Feat] AuditService 구현으로 주요 여신 원장 데이터 변경 이력 추적(Audit Trail) 시스템 구축
  - [Security] MfaService 추가를 통한 TOTP 기반 2차 인증 체계 모의 로직 적용
  - [Util] PrivacyMaskingUtils 구축으로 주민번호 및 연락처 개인정보 비식별화 처리 로직 구현
  - [Refactor] CircuitBreakerService 도입으로 외부 API 장애 발생 시 Fallback 대응 체계 마련
  - [Test] 감사 로그 기록, 개인정보 마스킹, MFA 검증 로직 단위 테스트 코드 작성
  - [DB] 스케줄, 담보, 가조회, QnA, 전자서명 등 신규 도메인 테이블 통합 생성 DDL 스크립트 작성
  - [Config] Springfox 기반 Swagger 3.0 연동 및 REST API 규격 문서화 자동화 환경 구축
  - [AOP] LoggingAspect를 통한 컨트롤러/서비스 계층 메서드 실행 시간 및 호출 이력 전역 로깅 체계 구현
  - [Config] Docker Compose Redis 컨테이너 연동을 위한 Lettuce 기반 RedisTemplate 스프링 설정 추가
  - [Feat] LoanDisbursementService 추가로 최종 대출금 타행 이체 송금 연동 및 ACTIVE 상태 전환 로직 구현
  - [Feat] RepaymentSchedulePersistenceService를 활용한 월별 상환 스케줄 DB 영구 적재 처리
  - [Feat] NotificationService 구축을 통한 고객 휴대전화 알림톡 발송 모의 API 연동
  - [Feat] DailyInterestScheduler 배치 스케줄러를 통한 정상 실행 채권 일일 정규 이자 결산 로직 추가
  - [Test] 대출금 송금, 스케줄 DB 저장, 알림톡 발송, 이자 결산 배치 단위 테스트 코드 작성
  - [Feat] LoanProductService 구현으로 상품별 금리/한도 가이드라인(신용, 주택담보, 햇살론) CMS 구축
  - [Feat] ContractPdfService 구축을 통한 고객 데이터 기반 대출 약정서 PDF 실시간 생성 로직 구현
  - [Feat] SmsOtpService 연동으로 휴대폰 번호 기반 2차 인증번호 발송 및 검증 보안 프로세스 추가
  - [Refactor] AdminDashboardController 내 차트 통계 데이터와 실제 DB 집계 쿼리 연동 준비
  - [Test] 상품 목록 조회, PDF 생성, OTP 인증 로직 JUnit 단위 테스트 케이스 추가
  - [Feat] Chart.js를 활용한 관리자 전용 대출 통계 대시보드(AdminDashboardController, dashboard.jsp) 구현
  - [Infra] Tomcat 9 및 JDK 21 기반 운영 환경 배포를 위한 Dockerfile 및 docker-compose.yml 구성
  - [Infra] GitHub Actions를 활용한 메이븐(Maven) 자동 빌드 및 CI(지속적 통합) 파이프라인 구축
  - [Feat] NplService 구현을 통한 장기 연체 채권 대손상각(Write-off) 및 외부 매각(Sale) 원장 처리
  - [Feat] AmlService 구축으로 자금세탁방지(AML) 및 금융사기 블랙리스트 검증 로직 적용
  - [Feat] DocumentVerificationService 추가를 통한 비대면 제출 서류(PDF/Img) 메타데이터 위변조 모의 탐지
  - [Feat] ChatbotService 구현으로 키워드 매칭 기반 24시간 자동 응답 고객 상담 API 연동
  - [Test] NPL 원장 처리, AML 검증, 서류 위변조 탐지, 챗봇 응답 로직 단위 테스트 케이스 추가
  - [Feat] OcrService를 통한 신분증 이미지 텍스트 추출 및 진위확인 모의 로직 적용
  - [Feat] ElectronicSignatureService 구축으로 약정서 전자서명 SHA-256 해시값 생성 및 보관 구현
  - [Feat] RefinancingService를 활용한 타행 대환대출 원장 이관 로직 구현
  - [Feat] EarlyWarningScheduler 연동을 통한 고객 신용등급 급락 조기 경보(EWS) 시스템 스케줄링 구축
  - [Test] OCR 텍스트 추출, 전자서명 생성, 대환대출, 조기경보 스케줄러 단위 테스트 케이스 추가
  - [Feat] RolloverService 구현을 통한 대출 만기 연장 심사 및 상태 갱신 로직 적용
  - [Feat] JdbcTemplate 기반 CollateralService 담보물 정보 DB 매핑 로직 추가
  - [Feat] RestTemplate을 활용한 ExternalCreditApiClient 외부 신용평가망 비동기 HTTP 통신 모듈 구축
  - [Feat] LoanCancellationService 청약 철회(Cancellation) 시 원장 금액 정산 로직 구현
  - [Test] 만기 연장, 담보 등록, API 클라이언트, 청약 철회 로직 JUnit 테스트 케이스 추가
  - [Feat] InterestRateService 구현을 통한 신용점수 구간별 변동 금리 및 максимально도 동적 산출 로직 적용
  - [Feat] PreInquiryService 구축으로 대출 가조회(Soft Inquiry) 실행 이력 DB 적재 처리
  - [Feat] EarlyRepaymentService를 활용한 조기 상환 수수료 일수 비례 계산식 구현
  - [Feat] QnAService 연동을 통한 고객 문의 게시판 및 관리자 답변 처리 프로세스 구현
  - [Test] 신용평가 기반 금리 산출, 가조회, 중도상환, QnA 로직 단위 테스트 케이스 추가
  - [Feat] JSTL 및 Bootstrap을 활용한 고객용 마이페이지(mypage.jsp, login.jsp) UI 구현
  - [Feat] CustomerAuthController 및 CustomerAuthInterceptor를 통한 고객 세션 인증 라우팅 구축
  - [Feat] PenaltyScheduler를 통한 연체 건(OVERDUE) 대상 일일 가산 이자(Penalty) 부과 배치 스케줄러 구현
  - [Feat] WebhookController를 통한 외부 가상계좌망 입금 콜백(Callback) 수신 REST API 통신 모듈 구축
  - [Test] 신규 기능(고객 인증, 이자 부과 스케줄러, 웹훅 API) 단위 테스트 케이스 추가
  - [Test] 신규 기능(수납 처리, 계좌 인증, 약관 동의, B2C 컨트롤러) 단위 테스트 케이스 추가
  - [Feat] PaymentService 구현을 통한 대출금 수납 잔액 차감 및 연체(OVERDUE) 상태 일괄 전환 처리
  - [Feat] AccountVerificationService 구현으로 1원 송금 방식 본인 계좌 난수 인증 로직 구축
  - [Feat] ConsentService를 활용한 신용정보 조회 약관 동의 이력(IP, Timestamp) DB 적재 로직 구현
  - [Feat] CustomerWebController 구축 및 고객 전용 B2C 마이페이지(대출 현황 및 상환 스케줄 조회) 라우팅
  - [Feat] 신용평가, 상환 스케줄, 이메일 통보 서비스를 대출 심사 핵심 프로세스(LoanReviewService)에 통합 연결
  - [Feat] 신용평가 모의 연동 API (CreditEvaluationService) 구현
  - [Feat] 원리금균등상환 방식 대출 상환 스케줄링 계산 로직 (RepaymentService) 구현
  - [Feat] JavaMailSender 활용 대출 승인 이메일 및 PDF 전자문서 첨부 발송 로직 구현
  - [Refactor] 대용량 데이터 처리 대비 Spring Batch 프레임워크 설정 및 Job 구성 추가

* **2026-04-03**
  - [Feat] @ControllerAdvice를 활용한 GlobalExceptionHandler 구축 및 커스텀 에러 화면(error/500.jsp) 연동
  - [Feat] MockHttpServletRequest를 활용한 예외 처리 핸들러 단위 테스트 구현
  - [Fix] 슬랙 알림 서비스 의존성 제거 및 전역 예외 처리 로직 최적화
  - [Feat] GitHub Actions 기반 CI/CD 파이프라인 구축
  - [Feat] JUnit5 및 Mockito 기반 비즈니스 로직 단위 테스트 구현
  - [Feat] Spring Security 및 JWT 기반 REST API 인증/인가 적용
  - [Feat] Dockerizing 및 docker-compose를 활용한 운영 환경 인프라(Tomcat 9, Redis) 통합 세팅
  - [Refactor] OS 환경(Windows/Linux)에 따른 파일 업로드 경로 및 외부 데이터 볼륨 동적 마운트 처리
  - [Feat] Spring WebSocket 기반 실시간 신규 대출 접수 알림(Toast) 기능 구현
  - [Feat] Chart.js 연동 및 관리자 대시보드 상태별 대출 통계 시각화 구현
  - [Fix] Spring Data Redis의 RedisCacheManager 생성자 버전 충돌 오류 및 팩토리 메서드 방식 전환으로 해결
  - [Feat] Spring Data Redis 연동 및 @Cacheable을 활용한 대출 목록 조회 캐싱 적용
  - [Feat] Spring Task(@Scheduled)를 활용한 대기 상태 대출건 자정 만료 처리 자동화 스케줄러 구현
  - [Feat] Springfox Swagger2 연동 및 WebMvcConfigurer 기반 API 명세서 자동화 UI 구축
  - [Fix] 파일 다운로드 컨트롤러 경로 매핑 및 JSP URL 404 에러 수정
  - [Feat] Spring AOP(@Aspect, @AfterReturning)를 활용한 대출 신청 자동 감사 로그(Audit Log) 적재 기능 구현
  - [Fix] Mapper XML과 인터페이스 간 쿼리 중복 등록 충돌(IllegalArgumentException) 및 JSTL EL 변수 매핑 오류 해결
  - [Feat] @ControllerAdvice를 활용한 GlobalExceptionHandler 구축 및 커스텀 에러 화면(error.jsp) 연동
  - [Feat] Apache POI XSSFWorkbook을 이용한 관리자 대출 심사 내역 Excel(.xlsx) 추출 API 구현
  - [Feat] Spring i18n(SessionLocaleResolver, LocaleChangeInterceptor)을 활용한 심사 메모 시스템 다국어(한국어/영어) 처리 적용
  - [Refactor] Log4jdbc-log4j2 및 Logback 연동을 통한 파라미터 바인딩 SQL 쿼리 로깅 시각화 적용
  - [Refactor] properties 기반의 메시지 소스 분리로 JSP 하드코딩 제거 및 유지보수성 향상
  - [Feat] Spring i18n(SessionLocaleResolver, LocaleChangeInterceptor)을 활용한 심사 메모 시스템 다국어(한국어/영어) 처리 적용
  - [Refactor] properties 기반의 메시지 소스 분리로 JSP 하드코딩 제거 및 유지보수성 향상

* **2026-04-02**
  - CommonsMultipartResolver를 활용한 업로드 파일 용량 제한(10MB) 설정r
  - 파일 저장 물리 경로(upload.properties) 외부 프로퍼티화 및 @Value 어노테이션 연동
  - [Fix] 프로젝트 구조에 맞지 않는 Java Config 제거 및 XML 기반 설정(Interceptor/Task)으로 회귀
  - [Feat] SHA-256 암호화 유틸(CryptoUtil) 도입 및 세션 기반 관리자 인증 로직 구현
  - [Feat] Spring Task(<task:annotation-driven>)를 활용한 10초 주기 심사 대기 건 모니터링 배치 구축
  - [Feat] Zxing 기반 QR 코드 생성 API 및 html2pdf.js 활용 승인 확인서 PDF 다운로드 기능 연동
  - SHA-256 해시 알고리즘(CryptoUtil)을 적용한 관리자 인증 및 암호화 로직 구현
  - Spring HandlerInterceptor(AdminInterceptor)를 활용한 세션 기반 미인증 사용자 대시보드 접근 차단(Routing) 적용
  - WebMvcConfigurer를 통한 전역 인터셉터 패턴 등록 및 로그인 페이지(login.jsp) 컨트롤러 구축
  - @EnableScheduling 적용 및 LoanScheduler 신규 생성을 통한 PENDING 상태 건 모니터링 배치 잡(Batch Job) 구현
  - Google Zxing 라이브러리를 활용한 대출 접수증 QR 코드 렌더링 API(/api/qr/{id}) 신규 구현
  - html2pdf.js를 연동하여 대출 상세 정보 및 QR 코드를 포함한 승인 확인서 PDF 전자문서 다운로드 기능 구축
  - [Sync] 누락된 도메인 모델 및 매퍼(AuditLog, LoanMemo, LoanMapper) 원격 저장소 동기화
  - [Sync] DB 스키마(schema.sql), 로깅(logback.xml), 웹 설정(web.xml) 등 환경 설정 파일 최신화 적용
  - [Sync] 대출 신청 화면(apply.jsp) UI 개선 사항 반영
  - [Fix] 서비스 계층(LoanReviewService) 매퍼 메서드명 불일치 컴파일 에러 복구
  - [Clean] RedisConfig 내 JedisConnectionFactory Deprecated 경고 해결 및 최신 설정(RedisStandaloneConfiguration) 적용
  - LoanWebController 내 /audit 엔드포인트 추가하여 감사 로그 조회 로직 연결
  - audit.jsp 신규 생성 및 JSTL을 활용한 시스템 감사 로그(Audit Log) 모니터링 대시보드 UI 연동
  - Fetch API(Ajax)를 활용한 대출 심사 비동기 메모(LoanMemo) 시스템 연동
  - @RestController 역할의 @ResponseBody 메서드를 LoanWebController에 추가하여 RESTful 메모 API(GET/POST) 구현
  - MyBatis @Insert, @Select 어노테이션 기반의 LoanMemoMapper 구축 및 H2 스키마(LOAN_MEMO) 동기화
  - Spring Cache(@CacheEvict)를 적용하여 대출 신청 및 승인 상태 변경 시 Redis 캐시 즉시 무효화(동기화) 처리
  - LoanWebController 내 대출 상세 조회(/detail/{id}) 및 관리자 승인(/approve) 엔드포인트 로직 구현
  - JSP(index, detail) 연동을 통한 대출 상세 뷰 진입 및 관리자 승인 기능 UI 구현
  - [Fix] LoanMapper 내 페이징, 동적 검색, 벌크 업데이트 메서드 구현 완료
  - [Fix] MyBatis @Select <script> 태그를 이용한 복합 쿼리 적용
  - [Clean] 서비스-매퍼 간 파라미터 타입 불일치(Update) 해결
  - [Fix] MyBatis Mapper 메서드(selectAllApplications 등) 어노테이션 누락 수정
  - [Success] H2 DB - MyBatis - Redis Cache 전체 파이프라인 정상 작동 확인
  - [Fix] root-context.xml 내 jdbc 네임스페이스 바인딩 오류 해결
  - [Clean] LoanWebController 내 미사용 import 제거 및 코드 최적화
  - [Test] Redis 캐시 키 생성 여부 최종 확인 및 H2 DB 스키마 자동 초기화 검증
  - [Fix] H2 인메모리 DB 테이블 미생성으로 인한 500 에러 해결 (schema.sql 자동 실행 설정)
  - [Fix] MyBatis Table Not Found 예외 처리 및 스키마 구조 동기화
  - [Fix] LoanReviewService 내 AuditLog 조회 메서드 누락분 구현
  - [Test] Docker 기반 Redis 서버와 Spring Cache(RedisCacheManager) 연동 확인 완료
  - [Fix] Java Config(RedisConfig) 도입을 통한 Redis 연동 및 XML 설정 충돌 해결
  - [Fix] AuditLog 도메인 모델 리팩토링 및 MyBatis 매퍼 파라미터 불일치 수정
  - [Feat] Chart.js를 활용한 메인 대시보드 대출 현황 시각화 구현
  - [Feat] 시스템 감사 로그(Audit Log) 추적 및 관리자 조회 기능 추가
  - [Fix] AuditLog 도메인 필드명 불일치 및 기본 생성자 누락 해결
  - [Fix] pom.xml 의존성(Jackson, Hibernate Validator) 중복 제거 및 버전 정규화
  - Lombok 의존성 제거 후 순수 자바 코드로 도메인 모델(Getter/Setter) 재구현
  - [Fix] AuditLogMapper 파라미터 타입 불일치 및 pom.xml 라이브러리 버전 중복 오류 해결
  - AOP 기반 감사 로그(Audit Log) 저장 로직 객체 지향 방식으로 리팩토링
  - [Fix] root-context.xml 내 Redis 포트 번호 파싱 에러(NumberFormatException) 해결
  - Redis Connection Factory 설정 최적화 및 로컬 도커 환경 동기화 완료
  - Spring Legacy 환경에 맞춘 Redis 기반 글로벌 캐싱 시스템 도입 (Spring Data Redis, Jedis 연동)
  - RedisCacheManager 설정 및 GenericJackson2JsonRedisSerializer를 활용한 객체 직렬화 적용
  - FileSystemResource를 활용한 증빙 서류 다운로드 컨트롤러(FileDownloadController) 추가
  - C:/upload 경로 기반의 파일 업로드/다운로드 프로세스 동기화 및 유효성 검사 강화
  - 파일 작업 시 MDC Trace ID 로깅 연동을 통한 추적성 확보
  - Logback RollingFileAppender를 적용하여 일자별 로그 파일 자동 생성 및 보관(30일) 정책 설정
  - Log4jdbc 불필요한 로그(audit, resultset) OFF 처리 및 쿼리 로깅 가독성 개선
  - Fetch API(Ajax)를 활용한 실시간 대출 한도 조회 기능 구현
  - 한도 초과 시 신청 버튼 비활성화 등 동적 UI 제어 적용
  - Hibernate Validator를 활용한 도메인 모델(@Valid, @NotBlank 등) 데이터 유효성 검증 로직 구현
  - Spring form 태그를 활용한 JSP 입력 폼 에러 메시지 바인딩 및 출력 처리
  - @ControllerAdvice 내 BindException 처리 핸들러 추가로 데이터 검증 실패 예외 상황 제어
  - 사용자 친화적인 에러 페이지(404, 500) UI 구현 및 web.xml 연동
  - Spring Security Crypto를 활용한 BCrypt 암호화 도입 및 세션 보안 정책 강화
  - Slf4j MDC(Mapped Diagnostic Context)를 활용한 요청별 고유 추적 ID(Trace ID) 로깅 시스템 구축
  - 커스텀 어노테이션(@LogExecutionTime) 및 Spring AOP 기반의 서비스 메서드 실행 시간 측정 프로파일러 구축
  - Springfox Swagger 2 연동을 통한 B2B Open API 문서 자동화 및 인터랙티브 테스트 환경(Swagger UI) 구축
  - Spring HandlerInterceptor를 활용한 JWT 기반 B2B 제휴사 전용 Open API(Stateless) 인증 및 인가 라우팅 구현
  - FontAwesome 및 Bootstrap Toast UI 적용, 대시보드 애니메이션 처리를 통한 프론트엔드 UX 고도화
  - jjwt 라이브러리를 활용한 JWT(JSON Web Token) 발급 API 구축 (Stateless 인증 기반 마련)
  - MyBatis 동적 쿼리를 활용한 대출 신청 내역 기간별 동적 검색(Date Range Search) 기능 추가
  - Spring AOP(@AfterReturning)를 활용한 관리자 활동 감사 로그(Audit Log) 자동 기록 아키텍처 구현
  - Spring @Async 및 ThreadPoolTaskExecutor를 활용한 외부 알림 발송 비동기 이벤트 처리 로직 구축
   
* **2026-04-01**
  - MyBatis <foreach> 동적 쿼리를 활용한 대출 신청 내역 다중 선택 및 상태 일괄 변경(Bulk Update) 기능 구현
  - @ControllerAdvice를 활용한 Global Exception Handler(전역 예외 처리) 구축 및 로깅 강화
  - 테스트 코드 호환성 유지를 위한 도메인 모델 생성자 복구 및 버그 픽스
  - Soft Delete(논리 삭제) 아키텍처 도입: 실제 데이터 삭제 대신 DEL_YN 상태값 관리를 통한 데이터 무결성 보장 
  - Spring Cache(@Cacheable, @CacheEvict)를 활용한 대시보드 통계 조회 쿼리 로컬 캐싱 및 DB 부하 최적화
  - Apache POI를 활용한 대출 신청 데이터 엑셀 파일(Excel) 일괄 업로드(Bulk Insert) API 구현
  - html2pdf.js를 활용한 대출 승인 확인서 전자문서(PDF) 자동 렌더링 및 다운로드 구현
  - Google Zxing 라이브러리를 활용한 대출 접수증 QR 코드 서버사이드 동적 생성(Image Rendering) 기능 구현
  - Fetch API(Ajax)를 활용한 관리자 대출 심사 비동기 메모(댓글) 기능 구현 (1:N 테이블 매핑)
  - Daum 우편번호 API 연동 및 클라이언트 측 폼 데이터 가공, DB 스키마(ADDRESS) 확장
  - MyBatis Mapper 인터페이스 컴파일 에러 복구 및 의존성 주입(DI) 실패 오류 해결
  - Chart.js를 활용한 메인 화면 대출 심사 현황(승인/거절/대기) 도넛 차트 시각화 및 대시보드 UI 고도화
  - Log4jdbc-log4j2를 활용한 SQL 쿼리 파라미터 바인딩 추적 및 포맷팅 로깅 적용
  - 보안 및 유지보수성 향상을 위한 환경 설정 외부 분리(Property Placeholder) 적용
  - HikariCP를 활용한 데이터베이스 커넥션 풀(Connection Pool) 환경 구축 및 성능 최적화
  - web.xml 설정을 통한 HTTP 상태 코드(404, 500) 사용자 정의 에러 페이지(Custom Error Page) 구현
  - JUnit 5 및 Spring TestContext Framework를 활용한 서비스 계층(Service Layer) 단위 테스트(Unit Test) 환경 구축
  - DB 인덱싱(IDX_LOAN_CUSTOMER_ID) 적용 및 검색 쿼리 성능 최적화
  - Java MessageDigest를 활용한 SHA-256 단방향 암호화(해시) 로그인 로직 구현
  - SLF4J 및 Logback 프레임워크 기반 표준 로깅 시스템 구축 (Console & File Appender)
  - Spring HandlerInterceptor를 활용한 관리자 인증(세션 검사) 및 페이지 접근 제어 적용
  - 서버에 업로드된 증빙 서류 파일 다운로드(File Download) 기능 구현 및 MyBatis 쿼리 누락(FILE_NAME) 수정
  - Spring MultipartResolver 및 Apache POI를 이용한 증빙 서류 파일 업로드 기능 구현
  - Apache POI 라이브러리를 활용한 대출 신청 내역 엑셀(Excel) 다운로드 기능 구현
  - Spring Task(`@Scheduled`)를 활용한 심사 대기(PENDING) 건 자동 모니터링 배치 스케줄러 구현
  - Spring Validation(`@Valid`)을 활용한 서버 사이드 폼 데이터 유효성 검사 적용
  - 서비스 계층 메서드 시그니처 변경에 따른 REST 컨트롤러 컴파일 에러 수정
  - 파일 누락/잘림으로 인한 컴파일 에러 복구 및 MyBatis `<where>`, `<if>`를 활용한 다중 조건 동적 쿼리(Dynamic SQL) 검색 필터 구현
  - 대출 신청 내역 목록 페이징(Pagination) 처리 로직 및 UI 구현
  - `@RestController` 및 Jackson 라이브러리를 활용한 JSON REST API 구축
  - Fetch API(Ajax)를 이용한 프론트엔드 비동기 데이터 통신 구현 (고객 대출 상태 중복 검사)
  - Spring AOP를 활용한 서비스 계층 자동 로깅(실행 시간 추적) 기능 구현
  - Spring `@Transactional`을 활용한 선언적 트랜잭션 관리 적용
  - Spring 의존성 주입(DI) 적용 및 MyBatis-H2 연동 완료 (메모리 리스트 제거)
  - 이클립스 XML Language Server 외부 리소스 다운로드 허용 및 Mapper XML 문법 오류 표기 해결
  - LoanMapper.xml DTD HTTPS 적용 및 XML 유효성 검사 오류 해결
  - MyBatis 프레임워크 연동 기초 설정 및 H2 데이터베이스 의존성 추가
  - 대출 신청 CRUD 처리를 위한 Mapper 인터페이스 및 SQL XML 매퍼 구현
  - 메인 화면 대시보드 통계(총 신청, 승인, 거절 건수) UI/UX 전면 개편
  - `@ControllerAdvice`를 활용한 전역 예외 처리(Global Exception Handling) 적용
  - 대출 신청 내역 고객 ID 검색 기능 구현
  - 대출 신청 내역 수정(Update) 로직 및 화면(edit.jsp) 구현하여 CRUD 사이클 완성
  - 대출 신청 데이터 삭제(취소) 기능 및 화면 구현
  - 대출 신청 단건 상세 조회(Detail) 화면 및 Controller/Service 로직 구현
  - 도메인 모델(LoanApplication) `getApplicationInfo()` 복구 및 Maven(pom.xml) JSTL 중복 오류 수정
  - 도메인 모델(LoanApplication) Getter/Setter 추가 및 Maven(pom.xml) JRE 컴파일러 버전 환경 오류 수정
  - JSTL 연동 및 대출 신청 내역 목록 조회(List) 화면 구현
  - UI 개선 (Bootstrap 5 적용하여 대출 신청 및 결과 화면 디자인 업그레이드)
  - Controller와 비즈니스 로직(Service) 연동 및 심사 결과 View 반영
* 2026-03-18: Spring Model 적용 및 결과 화면(JSP) 분리 구현
* **2026-03-09**: 대출 신청 프론트엔드 폼 및 데이터 수신 로직 구현
  - `apply.jsp` 대출 신청 폼 화면 생성 및 POST 전송 처리
  - `LoanWebController` 데이터 수신 확인 및 웹 통신 흐름 연결
* **2026-03-07**: Spring MVC 웹 환경 및 프론트엔드(JSP) 기초 세팅 완료
  - Eclipse Maven 프로젝트 구조 정상화 및 `WEB-INF/views` 보안 폴더 구조 적용
  - Tomcat 9.0 서버 연동 및 브라우저 JSP 화면 렌더링 테스트 완료
  - 컨트롤러 `@ResponseBody` 한글 깨짐 문제 해결 (UTF-8 인코딩 적용)
  - Maven 프로젝트 생성 (`pom.xml` 구성)
  - `LoanApplication`, `LoanReviewService` 등 핵심 비즈니스 로직 클래스 작성
  - 콘솔 환경에서의 대출 심사 로직 테스트 완료
  
---
## 🛠️ Development Log: Redis Cache Integration
* **도커(Docker) Redis 환경 세팅 완료**
  * Local 포트 `6379`를 도커 컨테이너와 매핑하여 Spring Data Redis 연동.
* **Troubleshooting: Port Conflict & Serialization**
  * 윈도우 로컬의 유령 Redis 프로세스와 도커 컨테이너 간의 포트(6379) 충돌 문제 해결. (`netstat -ano` 및 `taskkill` 활용)
  * `EOFException` 및 역직렬화 에러 방지를 위해 `RedisConfig`를 Java 설정으로 통일하고, `GenericJackson2JsonRedisSerializer`를 적용하여 캐시 데이터를 JSON 형식으로 안전하게 관리.
  
---

### 💡 실행 및 확인 방법
1.  Eclipse/IntelliJ에서 **Maven Project**로 Import
2.  `pom.xml` 의존성 업데이트 (Maven Update)
3.  Tomcat 9.0 서버 연동 후 실행
4.  관리자 계정: `admin` / 비밀번호: `admin123`