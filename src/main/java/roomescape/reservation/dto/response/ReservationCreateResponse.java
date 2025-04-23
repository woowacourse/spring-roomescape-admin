package roomescape.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.entity.ReservationEntity;

public record ReservationCreateResponse(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationCreateResponse of(ReservationEntity reservationEntity) {
        return new ReservationCreateResponse(reservationEntity.id(), reservationEntity.name(), reservationEntity.date(),
                reservationEntity.time());
    }
}
