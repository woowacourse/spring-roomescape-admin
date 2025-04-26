## 방탈출 예약관리

### 1단계 - 홈 화면

- localhost:8080/admin 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현.

### 2단계 - 예약 조회

- /admin/reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현
- 페이지 로드 시 호출되는 예약 목록 조회 API 구현

예약 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

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

### 3단계 - 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현.
- 추후 단계에서 API 명세 변경으로 해당 테스트 주석 처리.

예약 추가 API

Request

```
POST /reservations HTTP/1.1

content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
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
    "time": "15:40"
}
```

예약 취소 API

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

### 4단계 - 데이터베이스 적용하기

- h2 데이터베이스를 프로그램과 연결
- JUnit을 활용한 단위 테스트 작성

### 5단계 - 데이터 조회하기

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정
- 추후 단계에서 API 명세 변경으로 해당 테스트 주석 처리.

예약 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

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


### 6단계 - 데이터 추가 / 삭제하기

- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정
- 기존에 사용하던 List 및 AtomicLong 제거
- 예약 관리 기능이 정상 동작하도록 기능 완성
- 추후 단계에서 API 명세 변경으로 해당 테스트 주석 처리.

예약 추가 API

Request

```
POST /reservations HTTP/1.1

content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
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
    "time": "15:40"
}
```

예약 취소 API

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

### 7단계 - 시간 관리 기능

- API 명세를 따라 시간 관리 API 구현

시간 추가 API

Request

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

시간 조회 API

Request

```
GET /times HTTP/1.1
```

Response

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

시간 삭제 API

Request

```
DELETE /times/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

### 8단계 - 예약과 시간 관리

기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정

예약 추가 API

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

### 9단계 - 계층화 리팩터링

- 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리
- 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용
