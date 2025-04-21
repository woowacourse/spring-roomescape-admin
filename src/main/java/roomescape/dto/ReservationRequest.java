package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        validateDateTime(date, time);
        return new Reservation(null, name, LocalDateTime.of(date, time));
    }

    private void validateDateTime(final LocalDate date, final LocalTime time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("[ERROR] 예약일자와 시간은 반드시 입력해야 합니다.");
        }
    }
}
