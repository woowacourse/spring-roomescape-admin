package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(LocalDate date, String name, LocalTime time) {
    public static ReservationDto from(ReservationEntity entity) {
        return new ReservationDto(entity.date(), entity.name(), entity.time());
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(name, date, time);
    }
}
