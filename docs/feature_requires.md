# 1단계

## 구현할 기능 목록

* 예약 조회, 추가, 삭제

## 방탈출 예약 관리 비즈니스 정의

* 방탈출 고객이 관리자를 통해 방탈출 예약을 한다.
* 현재 방탈출은 한가지 카테고리의 방탈출만 존재한다.
  * 나중에 다른 유형의 방탈출이 추가 될 수 있다.
* 방탈출은 게임마다 시간이 다르다.
  * 현재는 한 가지 카테고리만 존재하므로 1시간으로 고정한다.

### Roomescape (방탈출 게임)

> 상태

* playingTime:PlayingTime
    * 방탈출 게임은 진행 시간을 가지고 있다.
* reservations:Reservations
  * 방탈출 게임은 시간표를 통해 예약 일정을 처리할 수 있다.

> 행위

* reserve - 방탈출 예약을 할 수 있다.
* cancelReservation - 방탈출 예약을 취소 할 수 있다.

### PlayingTime (방탈출 진행 시간)

> 상태

* playingTime:Duration
  * 방탈출 게임의 진행 시간을 관리한다.

> 행위 

* static toDefaultPlayingTime
  * 기본 플레이 시간을 반환한다.
  * 기본 값: 1시간
* static ofMinutes
  * 분을 기준으로 다양한 플레이 시간을 반환한다.
  * 플레이 시간의 다형성
* calculateEndTime
  * 예약 시간을 기준으로 끝나는 시간을 계산한다.

### Reservations (방탈출 시간표)

> 상태

* schedule:List<Reservation> 
  * 방탈출 시간표는 예약 정보로 일정을 관리할 수 있다.

> 행위 

* hasOverlapTime(ReservationTime)
  * 예약 일정 목록 중 예약 시간이 겹치는게 있는지 확인한다.

### Resrevation (예약 정보)

> 상태

* customerName:String
  * 예약자 이름
* reservationTime:ReservationTime
  * 예약 시간

> 행위

* isOverlapping(ReservationTime)
  * 예약 시간이 겹치는지 확인하는 작업을 위임한다.

  
### ReservationTime (예약 시간)

> 상태 

* startTime:LocalDateTime
* endTime:LocalDateTime

> 행위

* isOverlapping(ReservationTime)
  * 예약 시간이 겹치는지 확인한다.

