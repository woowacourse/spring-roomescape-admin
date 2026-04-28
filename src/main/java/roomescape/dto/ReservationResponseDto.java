package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.format.DateTimeFormatter;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        String time) {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponseDto from(Reservation reservation) {
        String name = reservation.getName().value();
        String date = reservation.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String time = timeFormatter.format(reservation.getTime());

        return new ReservationResponseDto(reservation.getId(), name, date, time);
    }
}
