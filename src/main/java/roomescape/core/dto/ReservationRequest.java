package roomescape.core.dto;

import java.time.LocalDate;
import roomescape.core.model.Reservation;
import roomescape.core.model.ReservationTime;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationRequest(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toEntity() {
        return new Reservation(name, date,
                new ReservationTime(timeId));
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
