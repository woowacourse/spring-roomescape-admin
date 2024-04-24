package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.time.domain.Time;

public class TimeResponse {
    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startAt;

    public TimeResponse() {
    }

    public TimeResponse(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponse toResponse(final Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeResponse timeResponse = (TimeResponse) o;
        return Objects.equals(id, timeResponse.id) && Objects.equals(startAt, timeResponse.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }
}
