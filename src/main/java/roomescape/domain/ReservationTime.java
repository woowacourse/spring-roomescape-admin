package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final String startAt;

    @JsonCreator
    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationTime that = (ReservationTime) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startAt != null ? startAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
