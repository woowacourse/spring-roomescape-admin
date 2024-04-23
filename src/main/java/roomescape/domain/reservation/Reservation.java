package roomescape.domain.reservation;

import roomescape.dto.ReservationResponseDto;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Name name, ReservationDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public ReservationResponseDto toResponseDto() {
        return new ReservationResponseDto(id, name.getName(), date.getDate(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
