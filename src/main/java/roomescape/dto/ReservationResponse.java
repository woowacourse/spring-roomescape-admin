package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {

    public static ReservationResponse toDto(final Reservation reservation) {
         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getCustomerName(),
                 reservation.getReservationDate(),
                 ReservationTimeResponse.toDto(reservation.getReservationTime())
         );
    }
}
