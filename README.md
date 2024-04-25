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
### 예약과 시간 관리
- [ ] 예약 시간은 시간 테이블에 저장된 값만 선택할 수 있다.
- [x] 예약 페이지 파일은 `templates/admin/reservation-legacy.html` 대신 `templates/admin/reservation.html`을 사용한다.
- [x] 외래키 지정을 통해 `reservation` 테이블과 `reservation_time` 테이블의 관계를 설정한다.
``` 
CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,                           -- 컬럼 수정
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id) -- 외래키 추가
);
```
- [ ] Reservation 클래스의 시간 타입을 ReservationTime 객체로 수정한다.
- [ ] 예약 추가 시, 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 ReservationTime의 식별자(ex. 1)로 수정한다.
- [ ] 예약 조회 시 ReservationTime 정보도 함께 조회하기 위해 아래와 같이 쿼리를 수정한다.
```
SELECT 
    r.id as reservation_id, 
    r.name, 
    r.date, 
    t.id as time_id, 
    t.start_at as time_value 
FROM reservation as r 
inner join reservation_time as t 
on r.time_id = t.id
```
- [ ] 예약 추가 API
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}

HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time" : {
        "id": 1,
        "startAt" : "10:00"
    }
}
```
- [ ] 예약 조회 API
```
GET /reservations HTTP/1.1

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    }
]
```
