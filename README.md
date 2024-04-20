# 방탈출 API 명세

## 예약 조회

### Request

- GET /reservations

### Response

- 200 OK
- content-ype: application/json

``` json
[
    {
        "id": 1,
        "name": "asd",
        "date": "2024-04-18",
        "time": "13:57:00"
    },
    {
        "id": 2,
        "name": "test",
        "date": "2024-04-18",
        "time": "14:00:00"
    }
]
```

---

## 예약 추가

### Request

- POST /reservations
- content-type: application/json

```json
{
  "name": "test",
  "date": "2024-04-18",
  "time": "14:00:00"
}
```

### Response

- 201 Created
- content-type: application/json

```json
{
  "id": 2,
  "name": "test",
  "date": "2024-04-18",
  "time": "14:00:00"
}
```

---

## 예약 삭제

### Request

- DELETE /reservations/{reservationId}
- Path Variables : Long

### Response

- 204 No Content

---

## 시간 추가

### Request

- POST /times
- content-type: application/json

```json
{
  "startAt": "10:00"
}
```

### Response

- 200 OK
- content-type: application/json

```json
{
  "id": 1,
  "startAt": "10:00"
}
```

---

## 시간 조회

### Request

- GET /times

### Response

- 200 OK
- content-type: application/json

```json
[
  {
    "id": 1,
    "startAt": "10:00"
  }
]
```

---

## 시간 삭제

### Request

- DELETE /times/{timeId}
- PathVariable : Long

### Response

- 200 OK
