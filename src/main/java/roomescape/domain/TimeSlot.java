package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

    private final Long id;
    private final LocalTime time;

    public TimeSlot(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public TimeSlot(Long id, String time) {
        this(id, LocalTime.parse(time));
    }

    public TimeSlot(String time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeSlot that = (TimeSlot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
