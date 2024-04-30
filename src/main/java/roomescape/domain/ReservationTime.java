package roomescape.domain;

import java.util.Objects;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime reservationTimeDto = (ReservationTime) o;
        return Objects.equals(id, reservationTimeDto.id) && Objects.equals(startAt, reservationTimeDto.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "Times{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
