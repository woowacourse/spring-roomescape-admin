package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;

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

    public Reservation toEntity(GameTime gameTime) {
        return new Reservation(name, date, gameTime);
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
