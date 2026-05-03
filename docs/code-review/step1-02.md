# Reply

## `인사말`

안녕하세요 웨지!

## `본문`

### 어떤 부분에 집중하여 리뷰해야 할까요?

####                

---

####                

## `마치며`

[피드백/답변 정리 문서](https://github.com/nn98/spring-roomescape-admin/blob/nn98/docs/code-review/step1-01.md)

- ### [📝 Feedback 01](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171918749) - Repository / DAO 분리의 의도
- ### [📝 Feedback 02](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171920507) - @Repository 어노테이션
- ### [📝 Feedback 03](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171925031) - DTO 를 활용한 계층 간 의존 제거
- ### [📝 Feedback 04](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171928189) - 쿼리의 분리, 반환값
- ### [📝 Feedback 05](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171932731) - 자료형 변경
- ### [📝 Feedback 06](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171933999) - 단위 테스트 구현
- ### [📝 Feedback 07](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171937953) - 자료형 변경
- ### [📝 Feedback 08](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171939298) - Util 클래스의 필요성
- ### [📝 Feedback 09](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171968584) - 저만의 효율적 기록 방식
- ### [📝 Feedback 10](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171986529) - Efficacy(효능)과 Effectiveness(유효성)
- ### [📝 Feedback 11](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171987469) - 스파이크(Spike)
- ### [📝 Feedback 12](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3171989344) - 따뜻한 공감

# 🛠️ 기능/구현

### 📝 Feedback 2-01

너무 추상적으로 리뷰를 드려서 멀리 다녀오셨네요  
Repository와 Dao의 차이점에 대한 학습으로 이어지신걸로 보이는데요

> 또한, 이 어노테이션은 @component의 특수한 형태이므로 클래스패스 스캔을 통해
> 스프링 컨테이너에 자동으로 빈(Bean)으로 등록됩니다

이 부분 때문에 리뷰를 남겼는데요, 컴포넌트 스캔을 통한 bean을 등록할 때  
구현체가 필요하기 때문에 인터페이스에 붙이는게 무용합니다.  
인터페이스에선 제거하고 구현체 쪽에만 붙여달라는 말이었어요.  
(구현체에는 이미 붙어있어서 동작이 잘 되던거고요)

### 💬 Apply 2-01

#### 헤메기

전 추상적인 리뷰 덕분에 더 많이 고민하고 이것저것 알아볼 수 있어서 좋았습니다! 😂  
인터페이스가 아닌 구현체에 붙여야 할 어노테이션임은 알았지만  
단순히 어노테이션만 이동하기보단   
언젠간 공부할 내용 한번 환기하기 + 항상 코드 꼼꼼하게 확인하기  
일거양득 할 수 있어서 좋았던 헤메기였어요.

---

### 📝 Feedback 2-02

이 Dao는 어떤 방향으로 "확장"될 수 있나요?
Mysql Dao, Redis Dao, message queue DAO를 만드나요?

이번 미션이 끝날 때 까지를 포함해서, 실제 프로덕션이라고 가정해도 이후 왠만한 요구사항으로는 JdbcReservationDao가 아니라 다른 확장은 존재하기 어려울 것으로 보이는데요.

설혹 확장된다손 치더라도 Repository의 추상화를 통해 도메인 영역에 대한 영향을 막을 수 가 있어서 의미가 없다고 생각하고요.
사실상 package-private인 객체여서 관계가 제한되는데 추상화를 씌워두는건 과추상이라고 생각합니다.

제 리뷰는 Dao 로직을 레포지토리로 옮기라는 게 아니라 아래 방식으로 구현해보라는 의미였는데요. 설명이 너무 모자랐네요.

```java
public class ReservationTimeDao implements ReserveationRepository { 
```

**학습 목적이 있으시니, 수정없이 이대로 적용해보시고 최종 미션까지 어떻게 이어지나 양상을 관찰해보시는 것도 좋겠네요. 리뷰를 반영하셔도 되고, 그대로 두셔도 됩니다.**

---

> 다만 제가 선제적으로 코드나 구현의 근거를 전달했다면 더 효과적으로, 더 효율적으로
> 진행 가능했을 부분이라 스스로 반성하게 되었습니다. ... 효율적인 리뷰를 위해 더 많은 시간을 배분하고 일정을 조율해야겠다 생각했습니다.

이 부분도 짚을만한 건 있는게요, 무조건 많은 컨텍스트를 전달하는 게 좋은 커뮤니케이션 방법은 아니라고 생각해요.

왜냐하면 저희는 최종 결과물인 코드를 가지고 있으니까요. 이걸 중심으로 소통해도 충분합니다.

사회생활에서 그 무엇보다 소통이 가장 중요한 덕목은 맞습니다. 그런데 제가 보기에 글렌은 이미 소통쪽에 강점을 가지고 있는 사람이어서요. 뭔가 더 많은 노력, 더 많은 정성을 추구하기 보단 '자원의 분배' 관점으로도
생각해볼 필요가 있겠어요.

작성해주신 apply 들과 PR 본문 커멘트를 갈아엎는 정성을 보면서, 여기에 들어갔을 리소스를 고려하게 되더라고요.
개발자는 비싼 인력이어서요. 어떤 action에 시간을 들였다면, 그 시간만큼의 기회비용이 발생해요.
지금은 학습의 시기여서 시간 자원을 투자하면 투자하는 대로 가치를 인정받는 시기지만, 임금을 지급받기 시작하면 '시간을 어떻게 배분했는가'에 대한 책임도 발생하게 되요.

PR 마지막에 이런 문장이 있던데요
> 웨지의 시간과 노력의 투자가 보람있을 수 있도록 노력

본인의 시간과 노력도 소중함을 인지해주세요. 그래야 '최적의 의사소통 비용'을 찾으려는 의식을 하게 될거에요.

### 💬 Apply 2-02

####                

---

### 📝 Feedback 2-03

> ResponseEntity raw type 활용이 있네요.  
> ResponseEntity 로 명시해주셔요.
>
> (ReservationController 에도 동일 건이 있습니다)

### 💬 Apply 2-03

#### raw type

쓰면서도 뭔가 찜찜한 기분이었는데 이게 raw type 문법이었군요.  
타입 안전성 측면에서 Void 명시가 필수적이고  
raw type 문법이 남은 이유는 제네릭 이전 코드와의 호환을 위해서임을 확인했어요!

---

### 📝 Feedback 2-04

DDD를 말씀하시던 분이 작성한 코드에 뜬금없이 왜 이런 객체가 돌아다니는지  
객체명으로 전체 검사 해봤다가 step-01 문서에서 답을 찾았는데요.   
제가 리뷰를 모호하게 드려서 발생한 문제네요.

일단 객체에 대한 리뷰를 드리면,

이 객체로 인해 서비스 레이어에서 ReservationJoinedDto를 직접 쓰는 사례가 생기잖아요.  
이러면 다른 개발자들은 Reservation과 ReservationJoinedDto 자기 기분대로 취사선택해 수정하는 일이 생깁니다.

나중되면 결국 같은 역할을 하는 두 객체를 모두 제거할 수 없는 일이 생겨요.  
우테코에선 Refactor 쉽게 하는데 회사가시면 변경 == 장애위험 이기 때문에요.  
괜히 건드려서 장애 때문에 1억 날리느니 그냥 두자. 하는 결정을 하게 됩니다

기존 리뷰에서 의도했던 건 ReservationService 객체에 달아둘게요.

### 💬 Apply 2-04

#### 협업+유지보수의 위험성

[피드백 02](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3177286318)
에서도, [피드백06](https://github.com/woowacourse/spring-roomescape-admin/pull/452#discussion_r3177320680) 에서도 언급되는 내용이지만  
환경과 상황을 기반으로 진짜 `실무적 효용성`을 명확히 인지하는 것이 가장 중요하겠네요.

제 딴에는 효율성을 향상하는 게 운영 측면에서 효율적이라 생각해 도입한 객체지만  
유지보수 측면에서 그보다 큰 문제를 야기할 수 있는 아이디어였군요..  
오브젝트에서 언급된 대로  
소프트웨어 분야에선 실무가 이론에 앞서나감을 상기하고  
OOP 의 근본적 목적인 유지보수 측면에 중점을 두도록 노력하겠습니다.

---

### 📝 Feedback 2-05

### 💬 Apply 2-05

####                
