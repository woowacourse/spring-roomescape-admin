package roomescape.dto;

import roomescape.model.Reservation;

public record ReservationRequestDto(String name, String date, String time) {

    public Reservation toEntity(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
