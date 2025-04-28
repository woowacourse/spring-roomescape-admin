# 요구사항 정리

### /amdmin 또는/ 요청시 index.html 반환

### DB를 사용하여 데이터 접근

-[x] JdbcTemplate을 이용하여 DataSource객체에 접근하기
-[x] DataSource 객체를 이용하여 Connection 확인하기
-[x] Connection 객체를 이용하여 데이터베이스 이름 검증
-[x] Connection 객체를 이용하여 테이블 이름 검증
-[x] 데이터베이스에 예약 하나 추가 후 예약 조회 API를 통해 조회한 예약 수와 데이터베이스 쿼리를 통해 조회한 예약 수가 같은지 비교하는 테스트
-[x] 예약 추가 API를 활용하여 테이블에 예약 정보 추가
-[x] 조회 쿼리를 이용하여 데이터가 저장되었는지 확인
-[x] 예약 취소 API를 활용하여 테이블에 예약 정보 삭제
-[x] 조회 쿼리를 이용하여 데이터가 삭제되었는지 확인


7단계 api 요구사항

시간만을 통해서 설정할 수 있게끔 한다.



### Request

```http request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}


```

### Response

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}

```

### Request

```http request
GET /times HTTP/1.1

```

### Response

```http request
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

### Request

```http request
DELETE /times/1 HTTP/1.1

```

### Response

```http request
HTTP/1.1 200
```
## 8단계

-[x] 기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정하세요.
-[x] templates/admin/reservation-legacy.html 대신 templates/admin/reservation.html 파일을 활용하세요.
-[x] 외래키 지정을 통해 reservation 테이블과 reservation_time 테이블의 관계를 설정해주세요.

## Request

```http request

POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}

```

## Response

```http request
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
## Request
```http request
GET /reservations HTTP/1.1
```
## Response

```http request
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
# 9단계

레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리를 해보세요.
분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용해보세요.
