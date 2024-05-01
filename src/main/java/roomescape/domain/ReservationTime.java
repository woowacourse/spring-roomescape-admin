package roomescape.domain;

import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ReservationTime that = (ReservationTime) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
