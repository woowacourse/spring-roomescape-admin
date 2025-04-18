package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record ReservationResponse(Long id, String name, String date, String time) {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";

    public static ReservationResponse toDto(Reservation reservation) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.KOREA);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.KOREA);

         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getName(),
                 reservation.getReservationDateTime().toLocalDate().format(dateFormatter),
                 reservation.getReservationDateTime().toLocalTime().format(timeFormatter)
         );
    }
}
