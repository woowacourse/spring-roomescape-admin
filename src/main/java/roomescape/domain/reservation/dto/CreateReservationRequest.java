package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record CreateReservationRequest(
    String name,
    LocalDate date,
    LocalTime time
) {

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }

    public Reservation toEntity() {
        return Reservation.createWithoutId(
            name,
            date,
            time
        );
    }
}
