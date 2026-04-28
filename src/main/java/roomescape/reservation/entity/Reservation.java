package roomescape.reservation.entity;

import roomescape.time.entity.ReservationTime;

public class Reservation {

    private Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(String name, String date, ReservationTime time) {
        return new Reservation(null, name, date, time);
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

    public ReservationTime getTime() {
        return time;
    }

    public Reservation toEntity(long id) {
        return new Reservation(
                id,
                name,
                date,
                time
        );
    }

}
