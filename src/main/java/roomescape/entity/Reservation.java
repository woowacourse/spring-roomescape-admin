package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.dto.ReservationRequestDto;

@Builder
public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final long timeId;

    public Reservation(final long id, final String name, final LocalDate date, final long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public long getId() {
        return id;
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
