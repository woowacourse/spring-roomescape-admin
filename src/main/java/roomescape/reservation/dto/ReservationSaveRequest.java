package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

public class ReservationSaveRequest {
    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationSaveRequest() {
    }

    public ReservationSaveRequest(final String name, final LocalDate date, final Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toReservation(final Time time) {
        return new Reservation(name, date, time);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
