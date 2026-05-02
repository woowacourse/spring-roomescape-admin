# 방탈출 예약 관리

## 기능 명세

### 예약 시간 (ReservationTime)

| Method | URI           | 설명             |
|--------|---------------|----------------|
| GET    | `/times`      | 전체 예약 시간 목록 조회 |
| POST   | `/times`      | 예약 시간 등록       |
| DELETE | `/times/{id}` | 예약 시간 삭제       |

요청 예시

```json
POST /times
{
  "startAt": "10:00"
}
```

응답 예시

```json
{
  "id": 1,
  "startAt": "10:00"
}
```

### 예약 (Reservation)

| Method | URI                  | 설명          |
|--------|----------------------|-------------|
| GET    | `/reservations`      | 전체 예약 목록 조회 |
| POST   | `/reservations`      | 예약 등록       |
| DELETE | `/reservations/{id}` | 예약 삭제       |

- 예약 등록 시 예약자명(`name`), 예약 날짜(`date`), 예약 시간 id(`timeId`) 를 전달하며, `timeId` 는 사전에 등록된 예약 시간 id 이다.
- 등록 성공 시 `201 Created` 와 함께 `Location: /reservations/{id}` 헤더, 응답 페이로드로 등록된 예약 정보를 반환한다.

요청 예시

```json
POST /reservations
{
  "name": "브라운",
  "date": "2026-05-10",
  "timeId": 1
}
```

응답 예시

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2026-05-10",
  "time": {
    "id": 1,
    "startAt": "10:00"
  }
}
```

## 실행 방법

### 요구 사항

- JDK 21 필요

### 애플리케이션 실행

```bash
# Windows
.\gradlew bootRun

# macOS / Linux
./gradlew bootRun
```

기본 포트는 Spring Boot 기본값인 `8080` 이다. 실행 후 `http://localhost:8080` 으로 API 에 접근할 수 있다.

### 빌드

```bash
gradle build
```

### 테스트 실행

```bash
gradle test
```