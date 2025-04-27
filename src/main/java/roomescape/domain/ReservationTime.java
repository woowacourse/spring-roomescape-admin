package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    @JsonCreator
    public ReservationTime(
            @JsonProperty("id") final Long id,
            @JsonProperty("startAt") final LocalTime startAt
    ) {
        this.id = id;
        this.startAt = startAt;
    }

    private ReservationTime(final String startAt) {
        this(null, LocalTime.parse(startAt));
    }

    public static ReservationTime create(final String startAt) {
        return new ReservationTime(startAt);
    }

    public ReservationTime register(final Long id) {
        return new ReservationTime(id, startAt);
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
        final ReservationTime that = (ReservationTime) o;
        return Objects.equals(id, that.id) && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt=" + startAt +
                '}';
    }
}
