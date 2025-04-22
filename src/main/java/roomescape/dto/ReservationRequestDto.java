package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        Long timeId
) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name(), this.date(), new ReservationTime(timeId, LocalTime.MIN));
    }
}
