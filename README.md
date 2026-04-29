# 방탈출 예약 관리

## 1단계: 웹 요청-응답

### 1. 예약 조회 기능

#### 구현 
- [x] 구현 완료

#### 메서드 / URL

- GET /reservations

#### 응답

```json
[
  {id, name, date, time}, 
  ...
]
```

### 2. 예약 추가

#### 구현
- [x] 구현 완료

#### 메서드 / URL
- POST /reservations

#### 요청 본문 

```json
{
  name, 
  date, 
  time
}
```

#### 응답

```json
{
  id, 
  name, 
  date, 
  time
}
```

### 3. 예약 삭제

#### 구현
- [x] 구현 완료

#### 메서드 / URL
- DELETE /reservations/{id}


#### 응답

- 200 OK

## 2단계: 데이터베이스 연동

### 1. 데이터베이스 연동

#### 구현
- [x] 구현 완료

### 2. 예약 dao를 메모리에서 h2로 변경

#### 구현
- [x] 구현 완료

## 3단계: 시간 관리

### 1. 시간 추가 기능

#### 구현
- [x] 구현 완료

#### 메서드 / URL
- POST /times

#### 요청 본문

```json
{startAt}
```

#### 응답

```json
{
  id, 
  startAt
}
```

### 2. 시간 조회 기능

#### 구현
- [x] 구현 완료

#### 메서드 / URL
- GET /times

#### 응답

```json
[
  {id, startAt}, 
  ...
]
```

### 3. 시간 삭제 기능

#### 구현
- [x] 구현 완료

#### 메서드 / URL
- DELETE /times/{id}

#### 요청 본문

```json
{
  name, 
  date, 
  time
}
```

#### 응답

- 200 OK

### 4. 예약과 시간 연결

#### 구현
- [ ] 구현 완료
