# 도메인과 엔티티(DDD)의 차이

엔티티는 도메인의 부분 집합이다. 그 중 엔티티는 식별 가능한 도메인을 칭한다. 그렇기에 도메인에 포함되는 엔티티도 비즈니스 로직, 즉 요구사항이 변경될 때 함께 수정된다. 

ReservationTime와 Reservation의 다른 점은 Reservation은 예약자명과 시간만으로 식별이 안되기 때문에 id가 별도로 필요하다. 하지만 ReservationTime은 시간 자체만으로 
식별이된다. 그렇기에 Reservation은 엔티티, ReservationTime은 VO에 가깝다. 