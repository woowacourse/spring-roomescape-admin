package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dto.ReservationRequest;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, String reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = reservationTime;
    }

    public Reservation(String name, String date, String reservationTime) {
        this.name = name;
        this.date = date;
        this.time = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
