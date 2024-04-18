package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationRequest() {
    }

    public ReservationRequest(final String name, final LocalDate date, final LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
