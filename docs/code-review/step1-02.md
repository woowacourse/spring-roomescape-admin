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

### 💬 Apply 2-02

####      

---

### 📝 Feedback 2-03

### 💬 Apply 2-03

####      

---

### 📝 Feedback 2-04

### 💬 Apply 2-04

####      

---

### 📝 Feedback 2-05

### 💬 Apply 2-05

####      
