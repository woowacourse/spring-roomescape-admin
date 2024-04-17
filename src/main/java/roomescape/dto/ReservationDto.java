package roomescape.dto;

import roomescape.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(Long id, String name, LocalDate date, LocalTime time) {
    public static ReservationDto from(final ReservationEntity reservationEntity) {
        return new ReservationDto(
                reservationEntity.getId(),
                reservationEntity.getClientName().getValue(),
                reservationEntity.getTime().toLocalDate(),
                reservationEntity.getTime().toLocalTime()
        );
    }
}
