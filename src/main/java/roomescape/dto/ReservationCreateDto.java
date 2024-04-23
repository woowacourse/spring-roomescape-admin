package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationCreateDto(String name, String date, Long timeId) {
    public Reservation toDomain() {
        return new Reservation(null, new Name(name), LocalDate.parse(date), new ReservationTime(timeId, null));
    }
}
