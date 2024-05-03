# 방탈출 예약 관리

## API 명세

### 예약 목록 조회 API

- Request

```
GET /reservations HTTP/1.1

```

- Response

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

### 예약 추가 API

- Request

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

- Response

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

### 예약 취소 API

- Request

```
DELETE /reservations/1 HTTP/1.1
```

- Response

```
HTTP/1.1 200
```

### 시간 추가 API

- Request

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

- Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API

- Request

```
GET /times HTTP/1.1
```

- Response

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

### 시간 삭제 API

- Request

```
DELETE /times/1 HTTP/1.1
```

- Response

```
HTTP/1.1 200
```

## 콘솔 UI

### 프로그램 실행

1. RoomescapeConsoleApplication을 실행하면 프로그램이 시작된다.
2. 현재 예약 현황과 예약 시간 현황 목록을 출력한다.
3. 선택 메뉴에서 원하는 항목을 선택한다.
   ```
   [방탈출] 원하시는 항목을 선택해 주세요. (ex.1)
   1. 예약 추가
   2. 예약 삭제
   3. 예약 시간 추가
   4. 예약 시간 삭제
   5. 프로그램 종료
   ```

### 예약 목록 현황

- 출력

```
[방탈출 예약 현황]
(ID) 이름 | 날짜 | 시간ID
```

```
[방탈출 예약 현황]
현재 예약 사항이 존재하지 않습니다.
```

### 예약 추가

- 규칙
    - 예약 시간이 존재하지 않을 경우, 다시 선택 메뉴로 돌아간다.
    - 예약자 방문 날짜는 "YYYY-MM-DD"로 입력해야 한다.
- 입력
  ```
  <예약 추가>를 선택하셨습니다.
  예약자 성함을 입력해 주세요.
  >>>
  예약자 방문 날짜를 입력해 주세요.
  >>>
  예약 시간 현황을 참고하여 예약할 시간 ID를 선택해 주세요.
  >>>
  ```
- 출력
  ```
  [예약 성공]
  예약이 완료 되었습니다.
  (ID) 이름 | 날짜 | 시간ID
  ```

### 예약 삭제

- 입력
  ```
  <예약 삭제>를 선택하셨습니다.
  예약 현황을 참고하여 삭제할 ID를 선택해 주세요.
  >>>
  ```
- 출력
  ```
  [예약 삭제 완료]
  ```

### 예약 시간 목록 현황

- 출력

```
[방탈출 예약 시간 현황]
(ID) 시간
```

```
[방탈출 예약 시간 현황]
현재 예약 시간이 존재하지 않습니다.
```

### 예약 시간 추가

- 규칙
    - 예약자 시간은 "HH:mm"로 입력해야 한다.
- 입력
  ```
  <예약 시간 추가>를 선택하셨습니다.
  추가할 예약 시간을 입력해 주세요.
  >>>
  ```
- 출력
  ```
  [예약 시간 추가 완료]
  예약 시간이 추가되었습니다.
  (ID) 예약 시간
  ```

### 예약 시간 삭제

- 입력
  ```
  <예약 시간 삭제>를 선택하셨습니다.
  예약 시간 현황을 참고하여 삭제할 ID를 선택해 주세요.
  >>>
  ```
- 출력
  ```
  [예약 시간 삭제 완료]
  ```
