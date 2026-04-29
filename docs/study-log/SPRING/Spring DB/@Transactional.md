### 개념
@Transactional 어노테이션은 트랜잭션에 대한 전파, 격리, 시간 초과, 읽기 전용 및 롤백 조건을 설정할 수 있고, 트랜잭션 관리자를 지정할 수 있음.

> 트랜잭션 간단 정리
>- 데이터베이스 작업을 '원자적으로 처리'하기 위한 메커니즘을 의미합니다.  
>- 여러 개의 데이터베이스 작업을 하나의 논리적 단위로 묶어서 실행하고 모든 작업이 성공적으로 완료하면 '커밋'하거나 실패할 경우 '롤백'하는 기능을 제공합니다.

### 속성

| **속성**            | **설명**                 | **속성 값**                                                                  |
| ----------------- | ---------------------- | ------------------------------------------------------------------------- |
| **propagation**   | 트랜잭션 전파 동작을 지정합니다.     | REQUIRED, SUPPORTS, MANDATORY, REQUIRES_NEW, NOT_SUPPORTED, NEVER, NESTED |
| **isolation**     | 트랜잭션 격리 수준을 지정합니다.     | DEFAULT, READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE  |
| **timeout**       | 트랜잭션 제한 시간을 지정합니다.     | 초 단위의 정수 값                                                                |
| **readOnly**      | 트랜잭션이 읽기 전용인지 지정합니다.   | true, false                                                               |
| **rollbackFor**   | 롤백을 일으키는 예외를 지정합니다.    | 예외 클래스                                                                    |
| **noRollbackFor** | 롤백을 일으키지 않는 예외를 지정합니다. | 예외 클래스                                                                    |

### 사용
- @Transactional 어노테이션을 메소드에 부여하면, 그 메소드 앞뒤가 트랜잭션 경계가 된다.
- 트랜잭션 경계 안에서 RuntimeException 및 그 서브 클래스가 throw 되면 트랜잭션은 롤백된다.
- @Transactional 어노테이션이 부여되어 있지 않으면, Exception 및 그 서브 클래스가 throw 되면 경우 롤백되지 않는다.
- Exception이 발생된 경우도 롤백되었으면 하는 경우는 @Transactional (rollbackFor = Exception.class)과 같이 설정한다.

### 적용 우선 순위
1. 클래스의 메서드
2. 클래스
3. 인터페이스의 메서드
4. 인터페이스