package roomescape.domain.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class ReservationRequestDTO {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    @JsonCreator
    public ReservationRequestDTO(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date, @JsonProperty("timeId") Long timeId) {
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

    public Long getTimeId() {
        return timeId;
    }
}
