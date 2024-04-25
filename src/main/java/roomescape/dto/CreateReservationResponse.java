package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record CreateReservationResponse(long id, String name, LocalDate date, ReservationTime reservationTime) {
}
