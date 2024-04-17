package roomescape.entity;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationEntity {
    private static final String TIME_FORMAT = "HH:mm";

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationEntity(long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
