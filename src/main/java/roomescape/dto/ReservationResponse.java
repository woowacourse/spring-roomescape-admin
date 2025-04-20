package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record ReservationResponse(Long id, String name, String date, String time) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);


    public static ReservationResponse toDto(final Reservation reservation) {

         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getCustomerName(),
                 reservation.getReservationDateTime().toLocalDate().format(DATE_FORMATTER),
                 reservation.getReservationDateTime().toLocalTime().format(TIME_FORMATTER)
         );
    }
}
