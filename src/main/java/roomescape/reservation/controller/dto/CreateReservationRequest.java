package roomescape.reservation.controller.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

public record CreateReservationRequest(
        String name,
        LocalDate date,
        long timeId
) {
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜를 입력하지 않으셨습니다.");
        }
        if (timeId <= 0) {
            throw new IllegalArgumentException("시간을 입력하지 않으셨습니다.");
        }
    }

    public Reservation toReservation(ReservationTime reservationTime) {
        return Reservation.createWithoutId(
                name,
                date,
                reservationTime
        );
    }
}
