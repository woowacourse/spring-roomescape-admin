package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationRequest(
        String name,
        String date,
        String time) {
    public Reservation toDomain(long id) {
        return new Reservation(id, name, date, time);
    }
}
