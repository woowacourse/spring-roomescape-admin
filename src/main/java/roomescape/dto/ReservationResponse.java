package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReserveTime;
import roomescape.entity.ReservationEntity;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(ReservationEntity entity) {
        Reservation reservation = entity.reservation();
        Long id = entity.id();
        Name name = reservation.getName();
        ReserveTime reserveTime = reservation.getReserveTime();
        return new ReservationResponse(id, name.asText(), reserveTime.getDate(), reserveTime.getTime());
    }
}
