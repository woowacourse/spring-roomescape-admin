package roomescape.dto;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {

    // TODO: 이름 바꾸던가 위치 바꿔야 함
    public static ReservationResponse from(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime
        );
    }
}
