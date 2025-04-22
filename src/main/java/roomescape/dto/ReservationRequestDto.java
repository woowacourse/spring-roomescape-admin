package roomescape.dto;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequestDto(LocalDate date, String name, LocalTime time) {
    public ReservationRequestDto {
        if (date == null || name == null || name.isBlank() || time == null) {
            throw new IllegalArgumentException("값이 모두 입력되지 않았습니다.");
        }
    }

    public Reservation toEntity() {
        return Reservation.of(name, LocalDateTime.of(date, time));
    }
}
