## 1단계: 웹 요청-응답

### 예약 CRUD API

| 기능 | 메서드 / URL | 요청 본문 | 응답 |
|------|--------------|----------|------|
| 예약 조회 | GET /reservations | - | `[{id, name, date, time}, ...]` |
| 예약 추가 | POST /reservations | `{name, date, time}` | `{id, name, date, time}` |
| 예약 삭제 | DELETE /reservations/{id} | - | `200 OK` |

### 예약 추가 요청·응답 예시
```json
POST /reservations HTTP/1.1
Content-Type: application/json

{
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

```json
HTTP/1.1 200
Content-Type: application/json

{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```
