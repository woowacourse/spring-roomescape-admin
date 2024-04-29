# 🚪 방탈출

## 📄 API 명세서

### 예약 조회

| HTTP Method | GET           |
|-------------|---------------|
| End Point   | /reservations |
| Status Code | 200 OK        |

#### Response Body

``` json
[
    {
        "id": long,
        "name": String,
        "date": String,
        "time": {
            "id: : long,
            "startAt" : String
        }
    },
    {
        ...
    }
]
```

### 예약 추가

| HTTP Method | POST          |
|-------------|---------------|
| End Point   | /reservations |
| Status Code | 201 Created   |

#### Request Body

``` json
{
    "name" : String,
    "date" : String,
    "timeId" : long
}
```

#### Response Body

``` json
{
    "id": long,
    "name": String,
    "date": String,
    "time": {
        "id: : long,
        "startAt" : String
    }
}
```

### 예약 취소

| HTTP Method | DELETE             |
|-------------|--------------------|
| End Point   | /reservations/{id} |
| Status Code | 204 No Content     |

#### Path Variable

```
id : long
```

### 시간 조회

| HTTP Method | GET    |
|-------------|--------|
| End Point   | /times |
| Status Code | 200 OK |

#### Response Body

``` json
[
    {
        "id": long,
        "startAt": String,
    },
    {
        ...
    }
]
```

### 시간 추가

| HTTP Method | POST        |
|-------------|-------------|
| End Point   | /times      |
| Status Code | 201 Created |

#### Request Body

``` json
{
    "startAt" : String
}
```

#### Response Body

``` json
{
    "id": long,
    "startAt": String
}
```

### 시간 삭제

| HTTP Method | DELETE         |
|-------------|----------------|
| End Point   | /times/{id}    |
| Status Code | 204 No Content |

#### Path Variable

```
id : long
```

---

## 콘솔 UI
- 콘솔 UI를 사용해 생성되는 데이터는 데이터베이스가 아닌 메모리에 저장합니다.

### 사용 방법
- `RoomescapeConsoleApplication`을 실행하면 콘솔 UI를 사용할 수 있습니다.

~~~markdown
[🚪 방탈출 관리 페이지]
예약이 없습니다.

시간이 없습니다.
메뉴를 선택해 주세요(ex. 1)
1. 예약 추가
2. 예약 삭제
3. 예약 시간 추가
4. 예약 시간 삭제
5. 종료
> 1

[예약 추가]
시간이 없습니다.

[🚪 방탈출 관리 페이지]
예약이 없습니다.

시간이 없습니다.

메뉴를 선택해 주세요(ex. 1)
1. 예약 추가
2. 예약 삭제
3. 예약 시간 추가
4. 예약 시간 삭제
5. 종료
> 3

[예약 시간 추가]
추가 할 예약 시간을 입력해주세요.(ex. 14:00)
> 13:00

[🚪 방탈출 관리 페이지]
예약이 없습니다.

시간 ID | 시간
   1   | 13:00

메뉴를 선택해 주세요(ex. 1)
1. 예약 추가
2. 예약 삭제
3. 예약 시간 추가
4. 예약 시간 삭제
5. 종료
> 1

[예약 추가]
시간 ID | 시간
   1   | 13:00

예약자를 입력해주세요.
> 리브
예약 날짜를 입력해주세요.(ex. 2024-04-27)
> 2024-04-30
예약 시간을 선택해주세요.(ex. 1)
> 1

[🚪 방탈출 관리 페이지]
예약 번호 | 예약자 | 날짜 | 시간
   1   | 리브 | 2024-04-27 | 13:00

시간 번호 | 시간
   1   | 13:00

메뉴를 선택해 주세요(ex. 1)
1. 예약 추가
2. 예약 삭제
3. 예약 시간 추가
4. 예약 시간 삭제
5. 종료
> 2

[예약 삭제]
예약 번호 | 예약자 | 날짜 | 시간
   1   | 리브 | 2024-04-27 | 13:00

삭제할 예약 번호를 입력해주세요.
> 1

[🚪 방탈출 관리 페이지]
예약이 없습니다.

시간 ID | 시간
   1   | 13:00

메뉴를 선택해 주세요(ex. 1)
1. 예약 추가
2. 예약 삭제
3. 예약 시간 추가
4. 예약 시간 삭제
5. 종료
~~~
