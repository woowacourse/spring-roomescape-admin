# 방탈출 예약 관리
Spring Boot와 JDBC Template을 사용해 방탈출 예약과 예약 시간을 관리하는 미션

## 기능 목록

- 예약 시간 생성, 조회, 삭제
- 예약 생성, 조회, 삭제
- 예약 생성 시 예약 시간 ID로 예약 시간 연결
- H2 메모리 데이터베이스 연동

## API

### 예약 시간

#### 예약 시간 생성

```http
POST /times
Content-Type: application/json

{
  "startAt": "10:00"
}
```

#### 예약 시간 목록 조회

```http
GET /times
```

#### 예약 시간 삭제

```http
DELETE /times/{id}
```

### 예약

#### 예약 생성

```http
POST /reservations
Content-Type: application/json

{
  "name": "브라운",
  "date": "2026-04-29",
  "timeId": 1
}
```

#### 예약 목록 조회

```http
GET /reservations
```

#### 예약 삭제

```http
DELETE /reservations/{id}
```
