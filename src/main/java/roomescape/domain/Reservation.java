package roomescape.domain;

import roomescape.dto.ReservationCreateDto;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(ReservationCreateDto dto, long id) {
        this.id = id;
        this.name = dto.getName();
        this.date = dto.getDate();
        this.time = dto.getTime();
    }

    public long getId() {
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
