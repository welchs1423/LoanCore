# LoanCore (여신 핵심 원장 시스템)

여신(대출) 업무의 전 과정(신청, 심사, 승인, 기표)을 처리하는 핵심 원장 관리 시스템 토이 프로젝트입니다. 실제 금융권의 레거시 개발 및 운영 환경을 모사하여 안정적인 구조로 설계되었습니다.

## 🛠 기술 스택 및 개발 환경 (Tech Stack & Tools)

### Backend
* **Language:** Java 25 (실무 환경을 고려한 Java 8 Compliance Level 적용)
* **Framework:** Spring Framework 5.x (XML / Java Config 기반)
* **Persistence:** MyBatis
* **Database:** RDBMS (Oracle 또는 PostgreSQL 예정)

### Build & Server
* **Build Tool:** Maven
* **WAS:** Apache Tomcat 9.0

### Tools
* **IDE:** Eclipse IDE for Enterprise Java and Web Developers
* **VCS:** Git, GitHub
* **API Test:** Postman

## 📝 업데이트 내역 (Update History)
* **2026-03-07**: 기본 브랜치명 변경 (master -> main) 및 GitHub 연동 완료
* **2026-03-07**: `LoanReviewService` 대출 심사 비즈니스 로직 뼈대 추가 및 Getter 메서드 적용
* **2026-03-07**: 프로젝트 초기 세팅, Git 알림 Hook 적용 및 `LoanApplication` 도메인 구현