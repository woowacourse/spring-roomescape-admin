package roomescape.reservationtime.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {

    private Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime createWithoutId(LocalTime startAt) {
        return new ReservationTime(null, startAt);
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
        ReservationTime reservationTime = (ReservationTime) object;
        return Objects.equals(getId(), reservationTime.getId()) && Objects.equals(getStartAt(),
                reservationTime.getStartAt());
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
