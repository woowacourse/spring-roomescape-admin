필수 개념 : 의존 관계
목표 : 도메인 간의 의존 관계를 이해하고, DB에서는 어떻게 영향이 미치는지 파악
내가 아는 것 : 외래키를 이용해 다른 도메인을 참조할 수 있다. DB에서는 1:1 또는 1:M 관계가 성립한다.

[외래키를 추가함으로써 변경된 사항 관찰]    
1. 우선 sql문으로 테이블의 필드가 변경되었을 때, 외래키를 추가한 Reservation 관련 테스트가 모두 깨졌다.
   이는 time을 string으로 받았는데 long으로 타입이 변환되서, 예약 추가 메서드가 제대로 작동하지 않아서이다.
2. 외래키를 매핑했기 때문에 reservation_time에 id필드를 추가했다.
   이를 매개로 접근해야해서 reservation_time은 필드로 id를 갖고 있는 것이 좋다. (reservation는 id 추가 안함)
3. 필드 이름이 바뀜에 따라 dto도 변경되었다
4. 테스트 에서도 기존에 Map<String, String> 으로 파라미터를 받았다면, String과 Long을 받기 위해 Map<String, Object>로 변경했다.   
   params.put("time", "10:00"); → params.put("timeId", 1L)
5. 쿼리문에서도 컬럼명에 따라 time_id로 수정해줘야했다.