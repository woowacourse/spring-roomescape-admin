# 🚀 미션 (방탈출 예약 관리)

## 📖 학습법
[학습법 로그](docs/study-log/README.md)

## ▶️ 두 가지 실행 모드

1. Spring Boot 기반 웹서버 모드 👉 [RoomescapeApplication.java](src/main/java/roomescape/RoomescapeApplication.java) 실행
2. Console 모드  👉 [ConsoleDriver.java](src/main/java/roomescape/console/ConsoleDriver.java) 실행

## 🌐 API

### 시간 API
| 기능       | 메서드 / URL         | 요청 본문   | 응답                            |
|------------|---------------------|------------|-------------------------------|
| 시간 추가  | POST /times         | {startAt}  | 201 Created / {id, startAt}   |
| 시간 조회  | GET /times          | -          | 200 OK / [{id, startAt}, ...] |
| 시간 삭제  | DELETE /times/{id}  | -          | 204 No Cotent                 |

### 예약 API
| 기능       | 메서드 / URL                | 요청 본문                | 응답                                          |
|------------|----------------------------|----------------------|---------------------------------------------|
| 예약 조회  | GET /reservations          | -                    | 201 Created / [{id, name, date, time}, ...] |
| 예약 추가  | POST /reservations         | {name, date, timeId} | 200 OK / {id, name, date, time}             |
| 예약 삭제  | DELETE /reservations/{id}  | -                    | 204 No Cotent                               |


## 🛠️ 기술 스택

| 분류 | 기술                                            |
|---|-----------------------------------------------|
| Language | Java 21                                       |
| Framework | Spring Boot 3.4.4                             |
| Build Tool | Gradle                                        |
| Web | Spring Web                                    |
| Database Access | Spring JDBC (JdbcTemplate)                    |
| Database | H2 Database                                   |
| Test | JUnit 5, Spring Boot Test, REST Assured 5.3.1 |
| SQL Logging | P6Spy                                         |


