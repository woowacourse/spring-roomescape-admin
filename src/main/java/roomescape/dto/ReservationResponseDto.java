package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record ReservationResponseDto(Long id, String name, String date, String time) {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public static ReservationResponseDto from(Reservation reservation) {

         return new ReservationResponseDto(
                 reservation.getId(),
                 reservation.getName(),
                 reservation.getDateTime().toLocalDate().format(dateFormatter),
                 reservation.getDateTime().toLocalTime().format(timeFormatter)
         );
    }
}
