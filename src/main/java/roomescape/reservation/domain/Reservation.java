package roomescape.reservation.domain;

import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Reservation(long id, String name, LocalDate date, ReservationTime time) {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Reservation(final long id, final String name, final String date, final ReservationTime time) {
        this(id, name, LocalDate.parse(date, DATE_FORMAT), time);
    }
}
