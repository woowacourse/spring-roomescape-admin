package roomescape.dto;

import roomescape.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
    public static ReservationDto from(ReservationEntity entity) {
        return new ReservationDto(entity.date(), entity.name(), entity.time());
    }

    public ReservationEntity toEntity() {
        return ReservationEntity.of(name, date, time);
    }
}
