# 4단계 Premortem

## 1. DTO -> 도메인 변환을 DTO 자체에서 한다.

### 대안

컨트롤러가 DTO를 도메인으로 변환한다.

### 이유

DTO가 자신을 바꾸는게 캡슐화와 코드 간결성 측면에서 더 좋아보인다.

### 틀렸을 때 증상

모르겠다.

### PR 직전 체크

DTO → 도메인 변환을 DTO 자체에서 하니 `ReservationRequest`가 ReservationTime을 알아야 했다.

```java
public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toDomain() {
        // timeId에 해당하는 ReservationTime을 넣어야 하는데 어떻게 넣어야 할지 모르겠다.
        return new Reservation(name, date, new ReservationTime(timeId, ???));
    }
}
```

예약 시간 객체가 있어야 Reservation 도메인을 만들 수 있는 상황이었다. 그에 따라 도메인 객체가 다른 도메인을 속성으로 가지는 경우 (특히 join으로 조회한 경우) 이 방식은 적합하지 않았다. 이를 해결하기 위해 다음 형태로 `toDomain()` 메서드를 바꿔보려고 한다.

```java
public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toDomain(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
```

의존 방향도 HTTP 요청 dto -> 도메인 객체이므로 방향 자체는 맞게 된다. 하지만 `toDomain()` 메서드가 ReservationTime을 파라미터로 받는 형태는 DTO가 도메인 객체에 의존하는 형태가 되어버린다. DTO가 도메인 객체를 알아야 하는 상황이 생긴 것이다. 이 부분이 옳은지 아닌지는 _아직 감이 오지 않는다._
