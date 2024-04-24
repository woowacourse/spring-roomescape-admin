## 예약 관리 어드민

------------------------------------------------------------------------------------------

### 예약

<details>
 <summary><code>POST</code> <code><b>/reservations</b></code> <code>신규 예약 생성</code></summary>

##### 요청 바디 정보

> | content-type        | 비고             |
> |---------------------|----------------|
> | `application/json`  | `유효한 시간 아이디를 입력`  |

##### 요청 바디 데이터 예시

```json
{
  "name": "영이",
  "date": "2024-05-12",
  "timeId": 1
}
```

##### 응답 정보

> | http code     | content-type                 | 비고                        |
> |---------------|------------------------------|---------------------------|
> | `201`         | `application/json`           | ` location 헤더에 리소스 위치 포함` |   

##### 응답 데이터 예시

```json
{
  "id": 2,
  "name": "영이",
  "date": "2024-05-12",
  "time": {
    "id": 1,
    "startAt": "12:30"
  }
}
```

</details>

<details>
 <summary><code>DELETE</code> <code><b>/reservations/{id}</b></code> <code>예약 삭제</code></summary>

##### 요청 경로 값 정보

> | name | data type | 비고               |
> |------|-----------|------------------|
> | id   | `number`  | `삭제할 예약 아이디를 입력` |

##### 응답 정보

> | http code | content-type | 비고                        |
> |-----------|--------------|---------------------------|
> | `204`     | `none`       |                           |   

</details>

<details>
 <summary><code>GET</code> <code><b>/reservations</b></code> <code>전체 예약 조회</code></summary>

##### 응답 정보

> | http code | content-type                 | 비고                        |
> |-----------|------------------------------|---------------------------|
> | `200`     | `application/json`           |                           |   

##### 응답 데이터 예시

```json
[
  {
    "id": 1,
    "name": "영이",
    "date": "2024-05-12",
    "time": {
      "id": 1,
      "startAt": "12:30"
    }
  },
  {
    "id": 2,
    "name": "영이",
    "date": "2024-05-12",
    "time": {
      "id": 1,
      "startAt": "12:30"
    }
  }
]
```

</details>

### 예약 가능 시간

<details>
 <summary><code>POST</code> <code><b>/times</b></code> <code>신규 예약 시간 생성</code></summary>

##### 요청 바디 정보

> | content-type       | 비고 |
> |--------------------|----|
> | `application/json` |    |

##### 요청 바디 데이터 예시

```json
{
  "startAt": "12:30"
}
```

##### 응답 정보

> | http code     | content-type                 | 비고                        |
> |---------------|------------------------------|---------------------------|
> | `201`         | `application/json`           | ` location 헤더에 리소스 위치 포함` |   

##### 응답 데이터 예시

```json
{
  "id": 1,
  "startAt": "12:30"
}
```

</details>

<details>
 <summary><code>DELETE</code> <code><b>/times/{id}</b></code> <code>예약 시간 삭제</code></summary>

##### 요청 경로 값 정보

> | name | data type | 비고              |
> |------|-----------|-----------------|
> | id   | `number`  | `삭제할 시간 아이디를 입력` |

##### 응답 정보

> | http code | content-type | 비고                        |
> |-----------|--------------|---------------------------|
> | `204`     | `none`       |                           |   

</details>

<details>
 <summary><code>GET</code> <code><b>/times</b></code> <code>전체 예약 시간 조회</code></summary>

##### 응답 정보

> | http code | content-type                 | 비고                        |
> |-----------|------------------------------|---------------------------|
> | `200`     | `application/json`           |                           |   

##### 응답 데이터 예시

```json
[
  {
    "id": 1,
    "startAt": "12:30"
  },
  {
    "id": 2,
    "startAt": "14:30"
  }
]
```

</details>

------------------------------------------------------------------------------------------
