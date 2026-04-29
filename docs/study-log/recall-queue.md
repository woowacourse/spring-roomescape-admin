


### 요청과 응답에서의 Jackson 직렬화
<details>

- **`@RestController`**: `@Controller + @ResponseBody`가 합쳐진 거.
    - 메서드 리턴값이 그대로 HTTP 응답 body로 직렬화됌.
    - List를 반환하면 Jackson이 JSON 배열로 만들어줌.
- **`@RequestBody Reservation request`**:
    - JSON body가 `Reservation` 객체로 역직렬화됌.
        - Jackson이 값을 채우며 방식이 여러가지가 있다.
            - 세터방식 - 세터와 기본생성자 필요
            - 필드 직접 주입 - 기본생성자 필요, 세터 없이 리플렉션으로 private 접근
            - 생성자 주입 - 권장방법(불변객체 설계가능), 객체생성 시 생성자로 바로 넣음
            - Builder 방식 - Lombok `@Builder` + Jackson 연동.
            - Java Record 방식 - canonical constructor로 바로 생성.
        - 지금은 기본 생성자로 직접 필드에 접근한다 정도로만 인식.
    - 동작은 하는데, "어떻게 채워지는 거지?"라는 흥미가 돋았지만 우선 호기심 큐에 넣음
        - 아래와 같이 의미없는 키-벨류가 들어와도 필요한것만 사용함.

        ```
        {
            "sdfsdf":"3sdf",
            "id": 3,
            "name": "브라운1",
            "date": "2023-08-05",
            "time": "15:40"
        }
        ```
</details>



### @DirtiesContext
<details>

- `@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)`가 붙어 있어서 매 테스트 전에 스프링 컨텍스트를 새로 만들어진다.
- 그래서 `예약_조회` 테스트가 먼저 실행되고 `예약_추가_및_삭제`가 실행돼도, 두 번째 테스트의 첫 POST가 `id=1`로 시작할 수 있는 것임
- 컨텍스트가 재생성되면서 `ReservationController` 빈도 새로 만들어지니까 `index`도 0부터 다시 시작.
- *"@DirtiesContext가 정확히 뭘 하는가, 왜 BEFORE_EACH_TEST_METHOD인가, 이거 없으면 어떻게 되나"*.

<details>

### AtomicLong
<details>
- 주요 개념 : `AtomicLong`은 **멀티스레드 환경에서 long 값을 안전하게 증가/감소/교체할 수 있게 만든 원자적(atomic) 클래스**
- 멀티스레드라는 개념이 등장. 콘솔환경과 웹환경 차이 관점으로 학습해보기.

<details>



