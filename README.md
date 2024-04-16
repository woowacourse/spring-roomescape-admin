# 방탈출 예약 관리

## 프로그램 설명
- 방탈출 예약을 관리할 수 있는 웹 애플리케이션을 만든다.
- 방탈출 예약을 조회할 수 있다.

## 1단계 기능 요구사항

- [x] `localhost:8080/admin` 요청 시, 어드민 메인 페이지를 응답한다.

## 2단계 기능 요구사항

- [ ] `/admin/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지가 응답한다.
- [ ] 예약 관리 페이지 로드 시 예약 목록 조회 API 호출한다.

### 예약 목록 조회 API

- Request
```
GET /reservations HTTP/1.1
```
- Response
```
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
