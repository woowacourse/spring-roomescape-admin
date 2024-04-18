## 방탈출

### 예약 조회

- HTTP Method: GET
- API Path: /reservations
- Status Code: 200 OK

### ResponseBody

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

### 예약 추가

- HTTP Method: POST
- API Path: /reservations
- Status Code: 200 OK

### RequestBody

```json
{
  "name": "test",
  "date": "2024-04-18",
  "time": "14:00:00"
}
```

### ResponseBody

```json
{
  "id": 2,
  "name": "test",
  "date": "2024-04-18",
  "time": "14:00:00"
}
```

---

### 예약 삭제

- HTTP Method: DELETE
- API Path: /reservations/{id}
- Status Code: 200 OK

### PathVariable

Type: Long

---
