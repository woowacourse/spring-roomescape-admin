package roomescape.dto;

import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

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
        return new Reservation(null, name, date,
                new ReservationTime(timeId, null));
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
