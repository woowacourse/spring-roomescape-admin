# 현재 미션 컨텍스트

## 미션 정보

- **미션명**: 방탈출 예약 관리 (spring-roomescape-admin)
- **레벨**: 레벨2
- **저장소**: spring-roomescape-admin

## 미션 단계

| 단계 | 핵심 주제 | 어려움의 성격 | 상태 |
|------|-----------|--------------|------|
| 1단계 | 웹 요청-응답 (MVC, 메모리 저장) | 환경·개념의 벽 — "스프링 프로젝트가 뭔지 모르겠다" | ✅ |
| 2단계 | DB 연동 (JdbcTemplate, H2) | API 사용법의 벽 — "JdbcTemplate 문법을 모르겠다" | ⬜ |
| 3단계 | 시간 관리 (도메인 간 의존, FK, JOIN) | 데이터 모델 확장의 벽 — "도메인 간 의존 관계를 어떻게 풀지 모르겠다" | ⬜ |
| 4단계 | 계층 분리 (Layered Architecture) | 설계 판단의 벽 — "어디를 분리해야 하는지 모르겠다" | ⬜ |

상태: ⬜ 진행 전 / 🔄 진행 중 / ✅ 완료

## 단계별 핵심 키워드

**1단계**
- `@ResponseBody`, `@RequestBody`, `@PathVariable`
- DTO, Controller return type
- `List`, `AtomicLong` (메모리 저장)
- 학습 테스트: spring-mvc-1, spring-mvc-2

**2단계**
- `JdbcTemplate`, `KeyHolder`, `SimpleJdbcInsert`
- H2 인메모리 DB, `schema.sql`
- SQL: SELECT, INSERT, DELETE
- 학습 테스트: JdbcTemplate

**3단계**
- INNER JOIN, 외래키(FK)
- 객체 간 의존 관계 (`Reservation` → `ReservationTime`)
- 테이블 간 의존 관계
- 키워드: inner join, 객체 의존, 외래키

**4단계**
- Layered Architecture: Controller / Service / DAO / Domain
- Spring Bean: `@Component`, `@Service`, `@Repository`
- Bean Container, DI
- 학습 테스트: spring-core-1

## 구현 원칙

- 빠르게 구현하고, 빠르게 PR을 보내라
- 완벽한 코드가 아니라 피드백을 받을 수 있는 코드가 목표
- 새로운 테스트 도구 도입 금지 (JUnit만, RestAssured는 주어진 것만)
- 단계 전환 시 학습 전략 재튜닝
