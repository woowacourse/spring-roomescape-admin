package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;

public record ReservationGetResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeGetResponse time
) {

    public static ReservationGetResponse from(Reservation reservation) {
        return new ReservationGetResponse(reservation.getId(), reservation.getName(), reservation.getDate(), ReservationTimeGetResponse.from(reservation.getTime()));
    }
}
