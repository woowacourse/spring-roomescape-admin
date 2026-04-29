## 학습 로그 #3

**시간**: 04/29 19:00 ~ 22:00 (약 180분) - 총 180분

**학습 범위**: 3~4단계: 시간 관리, 계층 분리

### 1. 막힌 것의 종류
이번에 막힌 것은 어떤 종류의 어려움이었는가? (해당하는 것에 체크)
- [X] 개념 자체를 모르겠다 (예: "스프링 빈이 뭔지 모르겠다")
- [X] 코드는 돌아가는데 이게 맞는 건지 모르겠다 (예: "계층 분리를 이렇게 해도 되나?")
- [X] 개념은 알겠는데 코드로 어떻게 쓰는지 모르겠다 (예: "JdbcTemplate 문법을 모르겠다")
- [ ] 기타: ___

### 2. 이번 타임의 학습 전략
- 이전에 바꾸기로 한 전략은 무엇이었고, 실행했는가?
  > 한단계 씩 더 공부를 한다고 진행을 했고, 한단계 씩 만 점진적으로 다가갔다.

- 실제로 어떻게 학습했는지 디테일한 과정을 써보세요.
  > 궁금한 개념을 정리하고, 한단계 씩 더 진행하고 만약에 더 깊이 들어간다 싶으면, 추후에 공부할 예정을 기입하였다.<br>

  > 요약 :
  > 1. 이전의 log2의 학습 방식과 동일하게 가져감
  > 2. 3에서는 i+1 정도로 한단계 씩 더 공부를 함.
  > 3. 추가적으로 더 깊게 들어가려하면 추후 공부 예정에 넣음


### 3. 전략 평가
- 효과적이었던 것과 그 이유
  > 이전의 학습 방법의 단점에서 깊이 못 갈 것 같다였는데, 한단계 씩 깊게 들어가다보니 자연스럽게 깊게 공부하게 됨.

- 비효과적이었던 것과 그 이유
  > 아직 크게 느끼지 못했는데, 문제점을 인식하는 것을 더욱 의식적으로 해야할 것 같다. 

- 막힌 것의 종류(1번)와 전략의 궁합은 어땠는가?
  > 궁합은 좋았다.<br>
  > 확실하게 기본적인 흐름을 알고 있어서, 개념과 코드를 간단하게 정리하면서 바로 작성해봤고,<br>
  > 이런 과정을 통해서 코드가 어떻게 돌아가는지에 대해서도 궁금해지면서, "왜 이렇게 쓰고, 어떻게 돌아가는지"까지 알 수 있었다.
  > 이전 로그에도 작성한 것인데 똑같은 장점이었다.

### 4. AI 피드백
- 자신의 학습 전략에 대해 AI 학습 전문가에게 피드백을 요청하고,
  유용했던 제안 1가지 이상 기록

```markdown
핵심 요약

현재 방식은 좋아짐.

기본 구현 → 한 단계 더 깊게 → 너무 깊으면 추후 학습으로 보류

이건 균형이 좋다.

다만 이번 로그의 핵심 문제는 하나임.

“문제점을 인식하는 기준”이 아직 감각에 의존한다.

그래서 개선점은 딱 하나:

체크리스트 기반으로 문제를 강제로 발견해라
원인 또는 개념 설명

지금은 이런 흐름이다.

궁금함 발생 → 공부
문제 느낌 → 리팩토링
더 깊음 → 추후 학습

좋은데, 약점이 있다.

궁금함이 생기지 않으면 문제를 못 본다.

계층 분리는 특히 그렇다.

코드가 돌아가도 잘못된 구조일 수 있다.

예를 들면:

Controller가 Repository를 직접 호출

이 코드는 돌아간다.
하지만 계층 분리 관점에서는 좋지 않다.

즉, 계층 분리는 오류로 알려주는 문제가 아니라 설계 감각으로 발견해야 하는 문제다.

멘탈 모델

“좋은 학습자는 답을 찾는 사람이 아니라, 이상 신호를 찾는 사람이다.”

DB 오류는 터진다.
컴파일 오류도 터진다.
하지만 설계 오류는 조용히 숨어 있다.

그래서 감각에만 맡기면 놓친다.

해결 방법 또는 적용 방법
개선안: “리팩토링 전 5문항 점검”을 추가해라

코드가 돌아간 뒤, 바로 끝내지 말고 딱 5개만 체크.

계층 분리 체크리스트
1. Controller가 비즈니스 판단을 하고 있나?
2. Controller가 Repository를 직접 호출하고 있나?
3. Service가 HTTP 요청/응답 DTO에 의존하고 있나?
4. Repository가 도메인 규칙을 판단하고 있나?
5. 테스트할 때 Fake Repository로 교체하기 쉬운 구조인가?

여기서 하나라도 YES면 리팩토링 후보.

예시
현재 코드
@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @PostMapping("/reservations")
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Reservation reservation = Reservation.of(
                request.name(),
                request.date(),
                request.time()
        );

        Reservation saved = reservationRepository.save(reservation);

        return ReservationResponse.from(saved);
    }
}

동작은 한다.
하지만 체크리스트를 적용하면:

2. Controller가 Repository를 직접 호출하고 있나? → YES

그러면 리팩토링.

개선 후
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.create(request);
        return ReservationResponse.from(reservation);
    }
}
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation create(ReservationRequest request) {
        Reservation reservation = Reservation.of(
                request.name(),
                request.date(),
                request.time()
        );

        return reservationRepository.save(reservation);
    }
}
앞으로 학습 루틴
1. 일단 구현
2. 동작 확인
3. 5문항 체크
4. 걸린 것만 리팩토링
5. 더 깊은 개념은 추후 학습으로 보류
   한 줄 정리

“코드가 돌아가는지 보지 말고, 역할이 섞였는지 봐라.”
```

### 5. 다음 타임에 바꿀 것
- 유지할 것과 그 이유
  > 일단 현재 방식에 대해서 지속적을 고수 할 예정입니다.<br>
  > 이전에 작성한 것 처럼 상세한 규칙을 생각하는 것이 중요하다고 느끼기 때문입니다.
  > 이것도 이전과 동일함.   

- 바꿀 것과 그 이유
  > 문제를 더욱 의식적으로 인식해볼 예정입니다.
