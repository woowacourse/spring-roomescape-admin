package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public class ReservationDto {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationDto(final Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().format(formatter);
    }
}
