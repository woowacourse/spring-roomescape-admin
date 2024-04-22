package roomescape.reservation.domain;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {
    private Long id;
    private LocalTime startAt;

    public TimeSlot(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public TimeSlot(final LocalTime startAt) {
        this.startAt = startAt;
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
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(id, timeSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", startAt=" + startAt +
                '}';
    }
}
