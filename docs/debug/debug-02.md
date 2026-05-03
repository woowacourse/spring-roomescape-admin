기대한 동작

- 예약 생성 테스트에서 먼저 reservation_time에 시간을 생성한다.
- 생성된 시간의 id를 이용해 예약 생성 요청을 보낸다.
- POST /reservations 또는 reservationController.create()는 name, date, timeId를 받아 reservation 테이블의 time_id 컬럼에 저장한다.
- 저장 후 생성된 예약 id와 연결된 시간 정보를 함께 담아 ReservationResponse를 반환한다.

실제 동작

- create()의 반환 타입을 ReservationResponse로 바꿨지만, 실제로 동작하지 못했다.
- 다른 의존되는 코드들 때문에 깨지고 있다.

현재 가설

- timeId에 해당하는 start_at을 다시 조회해서 제공해주고 이를 통해서 ReservationResponse를 만들어야 할 것 같았다.
