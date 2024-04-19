package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.dto.ReservationRequest;

public class Reservation {
    private static final String TIME_FORMAT = "HH:mm";

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(long id, ReservationRequest reservationRequest) {
        this(id, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
    }

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
    }

    public boolean hasSameId(long id) {
        return this.id == id;
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
