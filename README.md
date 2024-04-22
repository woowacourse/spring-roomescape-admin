## API 명세서

### 예약

<details>
<summary>POST <code>/reservations</code> 예약 추가 API</summary>

#### Request

```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

#### Response

```http
HTTP/1.1 201
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

</details>

<details>
<summary>GET <code>/reservations</code> 전체 예약 조회 API</summary>

#### Request

```http
GET /reservations HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    },
    {
        "id": 2,
        "name": "구름",
        "date": "2023-08-05",
        "time": {
            "id": 2,
            "startAt": "11:00"
        }
    }
]
```

</details>

<details>
<summary>DELETE <code>/reservations/{id}</code> 예약 삭제 API</summary>

#### Request

```http
DELETE /reservations/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204
```

</details>

### 예약 시간

<details>
<summary>POST <code>/times</code> 예약 시간 추가 API</summary>

#### Request

```http
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

#### Response

```http
HTTP/1.1 201
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

</details>

<details>
<summary>GET <code>/times</code> 전체 예약 시간 조회 API</summary>

#### Request

```http
GET /times HTTP/1.1
```

#### Response

```http
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    },
    {
        "id": 2,
        "startAt": "11:00"
    }
]
```

</details>

<details>
<summary>DELETE <code>/times/{id}</code> 예약 시간 삭제 API</summary>

#### Request

```http
DELETE /times/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204
```

</details>
