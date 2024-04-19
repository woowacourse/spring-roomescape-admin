package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private String name;
    private String date;
    private String time;

    public ReservationRequest() {
    }

    public ReservationRequest(final String name, final String date, final String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toVo(final Long id) {
        return new Reservation(id, name, date, time);
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
