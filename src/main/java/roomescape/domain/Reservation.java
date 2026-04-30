package roomescape.domain;

import roomescape.domain.vo.Name;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Long id;
    private final Name name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation() {
        this.id = null;
        this.name = null;
        this.date = null;
        this.time = null;
    }

    public Reservation(Reservation reservation, ReservationTime time) {
        this(reservation.getId(), reservation.getName().value(), dateFormatter.format(reservation.getDate()), time);
    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = new Name(name);
        this.date = translateDate(date);
        this.time = time;
    }

    private LocalDate translateDate(String date) {
        // TODO : 검증 로직

        return LocalDate.parse(date);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}