# 방탈출 예약 관리 프로그램

# 📖 API 목록

## 예약

### 1. 예약 목록 조회

- **URL**: `/reservations`
- **Method**: `GET`
- **Response**: `200 OK`
- **Response Body**:

```json
[
  {
    "id": 1,
    "name": "홍길동",
    "date": "2023-08-05",
    "time": "15:40"
  },
  ...
]
```

### 2. 예약 생성

- **URL**: `/reservations`
- **Method**: `POST`
- **Response**: `200 OK`
- **Request Header**:
    - `Content-Type: application/json`
- **Request Body**:

```json
{
  "name": "홍길동",
  "date": "2023-08-05",
  "time": "15:40"
}
```

### 3. 예약 삭제

- **URL**: `/reservations/{id}`
- **Method**: `DELETE`
- **Path Variable**:
    - `id` (Long) – 삭제할 예약의 고유 ID
- **Response**: `200 OK`
- **설명**: 해당 ID의 예약을 삭제합니다.

---

## 예약 시간

### 1. 예약시간 목록 조회

- **URL**: `/times`
- **Method**: `GET`
- **Response**: `200 OK`
- **Response Body**:

```json
[
  {
    "id": 1,
    "startAt": "10:00"
  },
  ...
]
```

### 2. 예약시간 생성

- **URL**: `/times`
- **Method**: `POST`
- **Response**: `200 OK`
- **Request Header**:
    - `Content-Type: application/json`
- **Request Body**:

```json
{
  "startAt": "10:00"
}
```

### 3. 예약시간 삭제

- **URL**: `/times/{id}`
- **Method**: `DELETE`
- **Path Variable**:
    - `id` (Long) – 삭제할 예약의 고유 ID
- **Response**: `200 OK`
- **설명**: 해당 ID의 예약시간을 삭제합니다.

---

# 📄 페이지 목록

- 웰컴 페이지 ``(/)``
- 관리자 화면 ``(/admin)``
- 예약 조회 화면 ``(/admin/reservations)``
- 시간 조회 화면 ``(/admin/time)``
