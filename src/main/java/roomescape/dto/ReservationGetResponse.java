package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;

public record ReservationGetResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeGetResponse time
) {

    public static ReservationGetResponse from(Reservation reservationEntity) {
        return new ReservationGetResponse(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(), ReservationTimeGetResponse.from(reservationEntity.getTime()));
    }
}
