package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

public record CreateReservationRequest(
    String name,
    LocalDate date,
    Long timeId
) {

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (timeId == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }

    public Reservation toEntity(ReservationTime reservationTime) {
        return Reservation.createWithoutId(
            name,
            date,
            reservationTime
        );
    }
}
