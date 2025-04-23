# 1, 2, 3단계

- [x] 다음 API를 구현한다

## 1. 메인페이지 응답

### Request

```
GET /admin HTTP/1.1
```

### Response

```
templates/admin/index.html
```

## 2. 예약페이지 응답

### Request

```
GET /admin/reservation HTTP/1.1
```

### Response

```
templates/admin/reservation-legacy.html
```

## 3. 예약 조회

### Request

```
GET /reservations HTTP/1.1

```

### Response

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

## 4. 예약 추가

- [x] `name`이 null일 시 예외처리한다
- [x] `date`가 null일 시 예외처리한다
- [x] `time`이 null일 시 예외처리한다

### Request

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

```

### Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

## 5. 예약 취소

- [x] 취소하려는 예약의 id가 존재하지 않을 시 예외처리한다

### Request

```
DELETE /reservations/{reservationId} HTTP/1.1
```

### Response

```
HTTP/1.1 200
```

---

# 4단계

- [x] 테이블 스키마를 다음과 같이 정의한다
- [x] h2 데이터베이스를 설정한다

# 5단계

- [x] 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용

# 6단계

- [x] 예약 추가 API 처리 로직에서 데이터베이스를 활용
- [x] 예약 취소 API 처리 로직에서 데이터베이스를 활용
    - [x] 취소하려는 예약의 id가 존재하지 않을 시 404 상태의 커스텀 예외 발생

# 7단계

- [x] `admin/time` 페이지로 접속 시 시간 관리 페이지를 반환한다
- [x] 다음과 같은 형식의 reservation_time 테이블을 생성한다
  ```angular2html
  CREATE TABLE reservation_time
  (
      id   BIGINT       NOT NULL AUTO_INCREMENT,
      start_at VARCHAR(255) NOT NULL,
      PRIMARY KEY (id)
  );
  ```
- [x] 시간 추가 API를 구현한다
    - [x] `startAt`이 null일 시 예외처리한다
      request
  ```angular2html
  POST /times HTTP/1.1
  content-type: application/json
  
  {
      "startAt": "10:00"
  }
  ```
  response
  ```
  HTTP/1.1 200
  Content-Type: application/json
  
  {
      "id": 1,
      "startAt": "10:00"
  }
  ```

- [x] 시간 조회 API를 구현한다

  request
  ```angular2html
  GET /times HTTP/1.1
  ```
  response
  ```
  HTTP/1.1 200
  Content-Type: application/json
  
  [
      {
          "id": 1,
          "startAt": "10:00"
      }
  ]
  ```

- [x] 시간 삭제 API를 구현한다
    - [x] 삭제하려는 예약시간의 id가 존재하지 않을 시 404 상태의 커스텀 예외 발생
      request
  ```angular2html
  DELETE /times/1 HTTP/1.1
  ```
  response
  ```
  HTTP/1.1 200
  ```

# 8단계

- [x] 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 한다
- [x] 외래키 지정을 통해 reservation과 reservation_time과의 관계를 설정한다
- [x] 예약 추가 API의 명세를 다음과 같이 수정한다

  Request
  ```
  POST /reservations HTTP/1.1
  content-type: application/json
  
  {
      "date": "2023-08-05",
      "name": "브라운",
      "timeId": 1
  }
  ```
  Response
  ```
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

- [x] 예약 조회 API의 명세를 다음과 같이 수정한다

  Request
  ```
  GET /reservations HTTP/1.1
  ```
  Response
  ```
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














