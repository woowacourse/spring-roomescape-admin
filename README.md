# 방탕출 예약 관리
## 홈 화면
### 요청
- 메서드 : GET
- 요청 URL : /admin
- 설명 : 어드민 페이지를 응답한다.

`GET /reservations HTTP/1.1`

## 예약 목록 조회
### 요청
- 메서드 : GET
- 요청 URL : /reservations
- 설명 : 예약 목록을 조회한다.

`GET /reservations HTTP/1.1`

### 응답
```json
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]
```

## 예약 추가  
### 요청
- 메서드 : POST
- 요청 URL : /reservations
- 설명 : 예약을 추가한다.
- 조건 
  - 이름
    - 이름은 null이 될 수 없다.
    - 이름은 10글자를 넘을 수 없다.
  - dateTime (날짜 & 시간)
    - 날짜와 시간은 null이 될 수 없다.
    - 과거 날짜로 예약할 수 없다.

```json
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

### 응답
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

## 예약 취소
### 요청
- 메서드 : DELETE
- 요청 URL : /reservations
- 설명 : 예약을 삭제한다.

`DELETE /reservations/1 HTTP/1.1`

### 응답
`DELETE /reservations/1 HTTP/1.1`
