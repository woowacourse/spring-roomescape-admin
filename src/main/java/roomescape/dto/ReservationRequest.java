package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public ReservationRequest {
        validate(name, date);
    }

    private void validate(final String name, final LocalDate date) {
        validateName(name);
        validateDate(date);
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 반드시 입력해야 합니다.");
        }
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 예약일자는 반드시 입력해야 합니다.");
        }
    }

    public Reservation toEntity(final ReservationTime reservationTime) {
        return Reservation.of(name, date, reservationTime);
    }
}
