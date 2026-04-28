package roomescape.reservation.dto;

import roomescape.reservation.Reservation;

public record ReservationRequestDto(
        String name,
        String date,
        String time
) {
    public Reservation toEntity(Long index) {
        return Reservation.of(index, this.name, this.date, this.time);
    }
}
