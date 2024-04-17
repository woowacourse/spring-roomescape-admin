package roomescape.domain;

import roomescape.dto.ReservationDto;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(final Long id, final String name, final String date, final String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public static Reservation toEntity(Long id, ReservationDto reservationDto) {
        return new Reservation(id, reservationDto.name(), reservationDto.date(), reservationDto.time());
    }
}
