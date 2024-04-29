# 방탈출 예약 관리

## WEB API 명세서

- 프로그램 시작 지점
  - [roomescape.RoomescapeApplication.java](./src/main/java/roomescape/RoomescapeApplication.java)

### 페이지 조회

| Path               | 페이지 설명           |
|--------------------|------------------|
| /admin             | 어드민 메인 페이지       |
| /admin/reservation | 어드민 예약 관리 페이지    |
| /admin/time        | 어드민 예약 시간 관리 페이지 |

### REST API

#### 예약 관리

- 예약 전체 조회
  - 요청
    ```text
    GET /reservations
    ```
  - 응답
    ```text
    200 OK
    
    [
      {
        "id" : 1,
        "name" : "브라운",
        "date" : "2023-08-05",
        "time" : {
          "id" : 1,
          "startAt" : "15:00"
        }
      }, 
      {
        "id" : 2,
        "name" : "브리",
        "date" : "2023-08-20",
        "time" : {
          "id" : 1,
          "startAt" : "15:00"
        }
      }
    ]
    ```
- 예약 생성
  - 요청
    ```text
    POST /reservations
    
    {
      "name" : "브리",
      "date" : "2023-08-20",
      "timeId" : 1
    }
    ```
  - 응답
    ```text
    201 Created
    
    {
      "id" : 2,
      "name" : "브리",
      "date" : "2023-08-20",
      "time" : {
        "id" : 1,
        "startAt" : "15:00"
      }
    }
    ```
- 예약 삭제
  - 요청
    ```text
    DELETE /reservations/2
    ```
  - 응답
    ```text
    204 No Content
    ```


#### 예약 시간 관리

- 예약 시간 전체 조회
  - 요청
    ```text
    GET /times
    ```
  - 응답
    ```text
    200 OK
    [
      {
        "id" : 1,
        "startAt" : "15:00"
      },
      {
        "id" : 2,
        "startAt" : "16:00"
      }
    ]
    ```
- 예약 시간 생성
  - 요청
    ```text
    POST /reservations
    
    {
      "startAt" : "16:00"
    }
    ```
  - 응답
    ```text
    201 Created
    
    {
      "id" : 2,
      "startAt" : "16:00"
    }
    ```
- 예약 시간 삭제
  - 요청
    ```text
    DELETE /reservations/2
    ```
  - 응답
    ```text
    204 No Content
    ```

## 콘솔 프로그램

- 시작 지점
  - [roomescape.RoomescapeConsoleApplication.java](./src/main/java/roomescape/RoomescapeConsoleApplication.java)

### 실행 예시

```text
도메인을 선택해주세요.
1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료
2

기능을 선택해주세요
1 - 전체 조회, 2 - 생성, 3 - 삭제
2

예약 시간 정보를 입력해주세요. (형식 : 시간
ex) 09:50
13:00
id :   1, start_at : 13:00

도메인을 선택해주세요.
1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료
1

기능을 선택해주세요
1 - 전체 조회, 2 - 생성, 3 - 삭제
2

예약 정보를 입력해주세요. (형식 : 날짜, 예약자, 시간 ID)
ex) 2023-08-10, 브리, 1
2023-08-10, 브리, 1
id :   1, name :     브리, date : 2023-08-10, start_at : 13:00

도메인을 선택해주세요.
1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료
1

기능을 선택해주세요
1 - 전체 조회, 2 - 생성, 3 - 삭제
2

예약 정보를 입력해주세요. (형식 : 날짜, 예약자, 시간 ID)
ex) 2023-08-10, 브리, 1
2023-08-20, 브라운, 1
id :   2, name :    브라운, date : 2023-08-20, start_at : 13:00

도메인을 선택해주세요.
1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료
1

기능을 선택해주세요
1 - 전체 조회, 2 - 생성, 3 - 삭제
1

※ 예약 현황 ※
id :   1, name :     브리, date : 2023-08-10, start_at : 13:00
id :   2, name :    브라운, date : 2023-08-20, start_at : 13:00

도메인을 선택해주세요.
1 - 예약 관리, 2 - 예약 시간 관리, 3 - 프로그램 종료
3
```
