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
        "time": String
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
| Status Code | 200 OK        |

#### Request Body
``` json
{
    "name" : String,
    "date" : String,
    "time" : String
}
```

#### Response Body
``` json
{
    "id": long,
    "name": String,
    "date": String,
    "time": String
}
```

### 예약 취소
| HTTP Method | DELETE             |
|-------------|--------------------|
| End Point   | /reservations/{id} |
| Status Code | 200 OK             |

#### Path Variable
```
id : long
```
