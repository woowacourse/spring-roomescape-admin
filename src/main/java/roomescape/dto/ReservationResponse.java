package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);

    public static ReservationResponse toDto(final Reservation reservation) {
         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getCustomerName(),
                 reservation.getReservationDate().format(DATE_FORMATTER),
                 ReservationTimeResponse.toDto(reservation.getReservationTime())
         );
    }
}
