package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;
import roomescape.dto.response.ReservationResponse;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toDomain(Long id) {
        return new Reservation(id, name, date, time);
    }
}
