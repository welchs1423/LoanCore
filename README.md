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

* **2026-04-01**
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