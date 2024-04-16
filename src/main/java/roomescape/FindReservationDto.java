package roomescape;

import java.time.format.DateTimeFormatter;

public record FindReservationDto(Long id, String name, String date, String time) {

    public static FindReservationDto of(final Reservation reservation) {
        return new FindReservationDto(
                reservation.getId().get(),
                reservation.getName(),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                reservation.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
    }
}
