package roomescape.time.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Time {

    private Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Time time = (Time) object;
        return Objects.equals(getId(), time.getId()) && Objects.equals(getStartAt(), time.getStartAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartAt());
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", startAt=" + startAt +
                '}';
    }
}
