package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public class ReservationCreateRequest {
    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationCreateRequest() {
    }

    public ReservationCreateRequest(String name, LocalDate date, long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }
}
