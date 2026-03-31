# 🏦 LoanCore - 여신(대출) 시스템 프로젝트

금융권 여신 업무를 모사하여 대출 신청, 심사, 승인 및 기표 과정을 관리하는 웹 애플리케이션 토이 프로젝트입니다.

## 🛠️ Tech Stack
* **Backend**: Java, Spring MVC, MyBatis
* **Frontend**: JSP, HTML/CSS, JavaScript (추후 jQuery 적용 예정)
* **Server**: Apache Tomcat 9.0
* **Database**: Oracle / PostgreSQL (예정)
* **Build Tool**: Maven

## 🚀 주요 기능 (구현 예정 포함)
* **대출 신청**: 고객 정보 및 신청 금액 입력
* **대출 심사**: 여신 심사 로직을 통한 승인/거절 처리
* **상태 관리**: 대출 상태(심사 중, 승인, 기표 대기 등) 추적 및 관리

### 📅 개발 진행 내역

* **2026-04-01**: Controller와 비즈니스 로직(Service) 연동 및 심사 결과 View 반영
  - UI 개선 (Bootstrap 5 적용하여 대출 신청 및 결과 화면 디자인 업그레이드)
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