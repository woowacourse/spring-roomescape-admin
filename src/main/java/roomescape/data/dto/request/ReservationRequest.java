package roomescape.data.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final long timeId;

    @JsonCreator
    public ReservationRequest(final String name, final LocalDate date, final long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
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
