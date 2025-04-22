package roomescape.dto;

import roomescape.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(LocalDate date, String name, LocalTime time) {
    public ReservationRequestDto {
        if (date == null || name == null || name.isBlank() || time == null) {
            throw new IllegalArgumentException("값이 모두 입력되지 않았습니다.");
        }
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(null, name, date, time);
    }
}
