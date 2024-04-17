package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public class ReservationResponseDto {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponseDto(final Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().format(formatter);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
