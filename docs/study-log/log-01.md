## 학습 로그 #1

**시간**: 04/28 15:30 ~ 17:00 (약 90분)
**학습 범위**: 1단계 MVC

### 1. 막힌 것의 종류
이번에 막힌 것은 어떤 종류의 어려움이었는가? (해당하는 것에 체크)
- [ ] 개념 자체를 모르겠다 (예: "스프링 빈이 뭔지 모르겠다")
- [ ] 개념은 알겠는데 코드로 어떻게 쓰는지 모르겠다 (예: "JdbcTemplate 문법을 모르겠다")
- [ ] 코드는 돌아가는데 이게 맞는 건지 모르겠다 (예: "계층 분리를 이렇게 해도 되나?")
- [x] 기타: 코드는 돌아가고, 대충 어떻게 사용하는건지 느낌은 알겠는데 궁금한게 많은 상태입니다.

### 2. 이번 타임의 학습 전략
- 이전에 바꾸기로 한 전략은 무엇이었고, 실행했는가?
  - 첫 번째 시도였고, 학습법 v3를 적용해보려 노력했습니다. 전략은 학습 테스트부터 써보는 전략이었습니다.
- 실제로 어떻게 학습했는지 디테일한 과정을 써보세요.
  - 학습 테스트를 사용해보며 기본적인 사용법을 익혔습니다. 이후에 요구사항을 충족시키기 위해 학습 테스트의 코드를 참고하여
    기능을 완성하였습니다.

### 3. 전략 평가
- 효과적이었던 것과 그 이유
  - Output 생성 단계가 효과적이었다고 생각합니다. 무엇을 알게 되었는지 정리해보고, 앞으로 어떤 부분을 추가로 학습할지
    나타내는 지표를 얻었습니다.
- 비효과적이었던 것과 그 이유
  - 검즘 & 회고 부분은 작성하기가 힘들었습니다. 요구사항을 충족시키는 문제를 해결하는 과정이었고, 요구사항을 충족한 시점에서
    기존에 정해놓았던 질문들이 적합하지 않다고 느꼈습니다. 간단하게 느낀 점과 생각을 적어 대체하였습니다.

- 막힌 것의 종류(1번)와 전략의 궁합은 어땠는가?
  - 아주 좋았다고 생각합니다. 궁금한게 많다는 것을 막연한 느낌에 그치지 않고 어떤게 궁금한지 정리해볼 수 있어서 학습법이 많이
    도움이 되었다고 생각합니다.

### 4. AI 피드백
- 자신의 학습 전략에 대해 AI 학습 전문가에게 피드백을 요청하고,
  유용했던 제안 1가지 이상 기록
  - 검증 & 회고를 ‘문제 해결용’에서 ‘오해 수정용’으로 바꿔라
    - 기존 질문:
      1. 예상 vs 실제
      2. 틀린 부분
      3. 다음 행동
    - 변경 제안 질문:
      1. 이번에 새로 알게 된 것 1개
      2. 아직 정확히 모르는 것 1개
      3. 다음에 확인할 것 1개

### 5. 다음 타임에 바꿀 것
- 유지할 것과 그 이유
  1. Output 생성
     - 지금 가장 잘 작동하고 있고, “궁금한 것 목록”이 자동 생성됨
  2. 얕게 끊는 학습
     - 미션 진행 속도 유지 및 과도한 탐구 방지
  3. 학습 테스트 기반 접근
     - 실행 중심 학습 유지 -> 바로 피드백 가능

- 바꿀 것과 그 이유
  - Output에 “전체 흐름 1줄 요약” 추가
    - 개별 개념은 정리가 되고있지만 전체 흐름은 정리되고 있지 않기 때문에 학습한 것과 관련된 흐름 요약 1줄을 추가해보는 전략 추가
    - "개념은 연결될 때만 재사용된다"
    - 솔직히 정확하게 어떤 의미인지 모르겠지만, 이거 한 줄 추가하는게 힘든 것은 아니라서 해보려고 합니다.

---

# 학습법 적용 산출물

## 1단계: 웹 요청-응답

### 0. 목표/기대 설정
- **메모리(`List` + `AtomicLong`)** 로 예약 상태를 관리한다.
- API 동작 확인 방법(테스트, HTTP 클라이언트 등)은 스스로 찾는다.
- 요구사항 테스트 통과

### 1. 즉시 적용

### 2. 막힘 해결
1. 예약 추가 및 삭제 테스트에서 문제 발생.
  - create()에서 reservation에 add()하고있지 않아 저장이 안되고 있었음
  - @DeleteMapping에 URL입력이 안되어 있었음

### 3. Output 생성
```java
@Controller  
public class ReservationController {  
    private List<Reservation> reservations = new ArrayList<>();  
    private AtomicLong index = new AtomicLong(1);  
  
    @GetMapping("/reservations")  
    public ResponseEntity<List<Reservation>> read() {  
        return ResponseEntity.ok().body(reservations);  
    }  
  
    @PostMapping("/reservations")  
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {  
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());  
        reservations.add(newReservation);  
        return ResponseEntity.ok().body(newReservation);  
    }  
  
    @DeleteMapping("/reservations/{id}")  
    public ResponseEntity<Void> delete(@PathVariable Long id) {  
        Reservation reservation = reservations.stream()  
                .filter(it -> it.getId().equals(id))  
                .findFirst()  
                .orElseThrow(RuntimeException::new);  
  
        reservations.remove(reservation);  
  
        return ResponseEntity.ok().build();  
    }  
}
```
- `ReservationController`
  - `@Controller` 어노테이션 적용
    - 컨트롤러니까 컨트롤러 어노테이션 적용.
    - `@RestController` 어노테이션 적용 시, 메서드에 `@ResponseBody` 어노테이션 생략 가능
      - 현재는 사용되지 않아서 사용하지 않았음
- `@XxxMapping` 어노테이션
  - 요청받을 URL을 매개변수?인자?로 작성 필요
  - Get - 조회, Post - 생성, Put - 전체 리소스 대체(수정), Patch - ==부분 수정?==, Delete - 삭제
- `read()`
  - 반환값을 `ResponseEntity`로 적용. ==왜? 궁금하지만 다음 기회에==
  - `ResponseEntity.ok().body(reservations)`
    - `ok()`: 상태 코드 결정 부분인 것 같다.
    - `body()`: HTTP응답 Body에 담을 객체를 전달하는 것 같다. ==JSON으로 무조건 변환되는건가?==
  - ==`ResponseEntity` 클래스에 대해 학습 필요==
- `create()`
  - 예제 코드를 따라 `Reservation`객체 생성 시, 정적 팩토리 메서드 사용
    - 요청받은 정보로 생성하는 과정으로 의미를 명확하게 하기 위해서 사용했다고 생각함. ==확인 필요==
  - ==`@RequestBody` 어노테이션==
    - 요청으로 들어오는 JSON 데이터를 객체로 변환해서 파라미터로 입력해줌
- `delete()`
  - ==`@PathVariable`==: 요청 URL에서 변수를 추출함
  - `ResponseEntity.ok().build();`: ==그냥 `build()`하면 어떻게 되는거지?==
  - 지금은 `RuntimeException` 날리고 있지만, 예외 처리는 어떻게 하는지 확인 필요

### 4. 검증 & 회고
- 스프링에 대한 깊은 이해가 없어도 테스트만 통과시키면 되는 미션이어서 문제 해결에 초점을 맞춰 빠르게 진행할 수 있었습니다.
  관련 자료 코드를 활용하여 진행하였지만 직접 코드를 쳐보며 기본적인 사용법에 익숙해졌다는 느낌이 듭니다.
  아직 궁금한 내용, 깊이있는 내용들에 대한 학습이 필요하지만, 우선 미션을 모두 완료시킨 뒤 추가 학습 해보려고 합니다.
