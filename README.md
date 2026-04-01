# 🏦 LoanCore - 여신(대출) 시스템 프로젝트

금융권의 핵심 업무인 여신(대출) 프로세스를 모사하여 대출 신청부터 심사, 관리자 승인 및 증빙 서류 관리까지 전 과정을 처리하는 **Spring 기반 백엔드 시스템**입니다.

### 🛠️ Tech Stack
- **Backend:** Java 11, Spring MVC 5.x, MyBatis 3.5, AspectJ (AOP), **Spring Cache (Redis / ConcurrentMapCache)**
- **Frontend:** JSP (JSTL), Bootstrap 5, JavaScript (Fetch API / Ajax), Chart.js 4.x, html2pdf.js
- **Database:** H2 Database (In-Memory), Spring JDBC, HikariCP (Connection Pool)
- **Security:** SHA-256 Hash Algorithm, Session-based Interceptor
- **Libraries:** Apache POI (Excel), Zxing 3.5 (QR Code), Log4jdbc-log4j2, Logback (SLF4J), Hibernate Validator
- **External API:** Daum/Kakao Postcode API 연동
- **Build Tool:** Maven
- **Server:** Apache Tomcat 9.0

### 🚀 주요 구현 기능

#### 1. 대출 프로세스 및 CRUD
- **대출 신청 및 주소 검색:** Daum 우편번호 API를 연동하여 정확한 거주지 정보를 입력받고 데이터 바인딩 처리
- **심사 로직:** 신청 금액 기준 자동 승인/거절 처리 및 상태 관리
- **심사 메모 시스템:** Fetch API(Ajax)를 활용하여 상세 페이지 내 비동기식 실시간 메모(댓글) 기능 구현
- **증빙 서류 관리:** MultipartResolver를 이용한 서류 업로드 및 **FileDownloadController를 통한 다운로드 스트림** 구현

#### 2. 관리자 및 보안 (Admin & Security)
- **데이터 통합 관리:** Pagination 및 Dynamic SQL을 활용한 조건별 검색 필터링 목록 조회
- **상태 일괄 변경:** MyBatis `<foreach>` 태그를 활용한 다중 선택 건의 상태 일괄 업데이트(Bulk Update) 구현
- **관리자 인증:** **AdminInterceptor를 활용한 세션 기반 접근 제어** 및 CryptoUtil 기반의 **SHA-256 비밀번호 암호화** 로그인
- **실시간 모니터링:** **@Scheduled 기반의 LoanScheduler**를 통해 심사 대기 건수 주기적 추적 및 로그 기록

#### 3. 시스템 운영 및 성능 최적화
- **통계 시각화 대시보드:** Chart.js를 활용하여 심사 현황을 가시화한 인터랙티브 도넛 차트 대시보드 구현
- **조회 성능 최적화:** **Spring Cache(@Cacheable) 및 Redis 연동**으로 중복 통계 쿼리의 DB 부하 최소화 및 응답 속도 개선
- **전자문서 및 QR 발급:** Zxing을 이용한 접수증 **QR 코드 생성** 및 html2pdf 기반의 **승인 확인서 PDF** 발급 기능
- **데이터 추출:** Apache POI를 활용한 대출 신청 내역 **Excel 다운로드** 기능 구현
- **견고한 예외 처리:** @ControllerAdvice 기반의 Global Exception Handler 구축으로 시스템 안정성 확보
- **로깅 및 모니터링:** Spring AOP 서비스 추적, Log4jdbc를 이용한 SQL 가시화 및 Logback 기록 관리

---

### 📅 개발 진행 내역

* **2026-04-02**
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