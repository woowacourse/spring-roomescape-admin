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
