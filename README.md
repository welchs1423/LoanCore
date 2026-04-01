# 🏦 LoanCore - 여신(대출) 시스템 프로젝트

금융권의 핵심 업무인 여신(대출) 프로세스를 모사하여 대출 신청부터 심사, 관리자 승인 및 증빙 서류 관리까지 전 과정을 처리하는 **Spring 기반 백엔드 시스템**입니다.

## 🛠️ Tech Stack

* **Backend:** Java 11, **Spring MVC 5.x**, **MyBatis 3.5**, **AspectJ (AOP)**
* **Frontend:** JSP (JSTL), **Bootstrap 5**, JavaScript (**Fetch API / Ajax**)
* **Database:** **H2 Database (In-Memory)**, Spring JDBC
* **Security:** **SHA-256 Hash Algorithm**, Session-based Interceptor
* **Libraries:** **Apache POI (Excel)**, **Apache Commons FileUpload**, **Logback (SLF4J)**, Hibernate Validator
* **Build Tool:** Maven
* **Server:** Apache Tomcat 9.0

---

## 🚀 주요 구현 기능

### 1. 대출 프로세스 및 CRUD
* **대출 신청:** 고객 정보 및 신청 금액 입력 (**Server-side Validation** 적용)
* **심사 로직:** 금액 기준 자동 승인/거절 처리 및 상태 관리
* **증빙 서류 관리:** MultipartResolver를 이용한 **파일 업로드 및 다운로드** 기능 구현
* **목록 조회:** MyBatis를 이용한 **페이징(Pagination)** 및 **동적 쿼리(Dynamic SQL)** 검색 필터

### 2. 관리자 및 보안 (Admin & Security)
* **관리자 인증:** **Spring Interceptor**를 활용한 비로그인 사용자 접근 제어
* **비밀번호 보안:** **SHA-256 단방향 암호화** 적용으로 관리자 계정 정보 보호
* **데이터 추출:** Apache POI를 활용한 전체 신청 내역 **Excel 다운로드** (.xlsx)

### 3. 시스템 운영 및 자동화
* **자동 로깅:** **Spring AOP**를 활용한 서비스 메서드 실행 시간 추적 및 **Logback** 기록 (Console/File)
* **배치 처리:** **Spring Task(@Scheduled)**를 이용한 1분 단위 심사 대기 건 자동 모니터링
* **트랜잭션:** `@Transactional` 적용으로 데이터 무결성 및 ACID 원칙 보장
* **성능 최적화:** 검색 빈도가 높은 컬럼에 **DB Index** 생성 및 검색 쿼리 최적화

---

### 📅 개발 진행 내역

* **2026-04-01**
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

### 💡 실행 및 확인 방법
1.  Eclipse/IntelliJ에서 **Maven Project**로 Import
2.  `pom.xml` 의존성 업데이트 (Maven Update)
3.  Tomcat 9.0 서버 연동 후 실행
4.  관리자 계정: `admin` / 비밀번호: `admin123`