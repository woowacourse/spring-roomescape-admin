package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record ReservationDto(long id, String name, LocalDate date, LocalTime time) { // todo ReservationResponse로 변경
    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
// todo controller 하위 디렉토리
