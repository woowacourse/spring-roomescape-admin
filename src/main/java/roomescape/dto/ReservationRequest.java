package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        String date,
        long timeId) {
    public Reservation toDomain(long id, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }
}
