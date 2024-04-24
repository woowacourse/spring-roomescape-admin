package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record CreateReservationResponse(int id, String name, LocalDate date, ReservationTime reservationTime) {
}
