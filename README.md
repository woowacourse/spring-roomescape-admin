# 구현할 기능 목록

- [x] `/admin` GET 요청 시 어드민 메인 페이지가 응답
- [x] `/admin/reservation` 요청 시 예약 관리 페이지가 응답
  - [x] `templates/admin/reservation-legacy.html` 파일 사용
- [x] 예약 조회 기능 구현
  ```
  GET /reservations HTTP/1.1
  
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
- [x] 예약 추가 기능 구현
    ```    
    POST /reservations HTTP/1.1
    
    content-type: application/json
    
    {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
    }
    
    HTTP/1.1 200 
    Content-Type: application/json
    
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": "15:40"
    }
    ```
- [x] 예약 취소 기능 구현
    ```
    DELETE /reservations/1 HTTP/1.1
    
    HTTP/1.1 200
    ```
- [x] h2 데이터베이스 연결
  - [x] console 기능 활성화
  - [x] datasource url을 `jdbc:h2:mem:database` 로 설정
- [x] 예약 조회 API에서 저장된 예약을 조회할 때 h2 데이터베이스를 활용한다.
  - 이 단계에서는 예약 추가/취소 API는 정상 동작 하지 않으니 해당 테스트는 건너뛴다.
- [x] 예약 추가/취소 API 로직에서 데이터베이스를 활용한다.
  - [x] 기존에 사용하던 List 및 AtomicLong을 제거한다.
  - [x] 예약 추가 시 예약 데이터 생성 후 생성된 id 값을 활용해서 응답한다.
### 시간 관리 기능 추가
- [x] `admin/time` 요청 시 페이지는 `templates/admin/time.html` 파일을 사용한다.
- [x] 시간 추가 API
```
POST /times HTTP/1.1
content-type: application/json

{
  "startAt": "10:00"
}

HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```
- [x] 시간 조회 API
```
GET /times HTTP/1.1

HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```
- [x] 시간 삭제 API
```
DELETE /times/1 HTTP/1.1

HTTP/1.1 200
```
- [x] 데이터 베이스 스키마 작성
```
CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```
