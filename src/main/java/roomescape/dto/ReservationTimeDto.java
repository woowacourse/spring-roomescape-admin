package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Objects;
import roomescape.domain.reservationtime.ReservationTime;

public class ReservationTimeDto {

    private final Long id;
    private final String startAt;

    @JsonCreator
    private ReservationTimeDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(
                reservationTime.getId(),
                reservationTime.getStartAt().toStringTime()
        );
    }

    public static ReservationTimeDto of(Long id, String startAt) {
        return new ReservationTimeDto(id, startAt);
    }

    public ReservationTime toDomain() {
        return ReservationTime.of(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationTimeDto other = (ReservationTimeDto) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.startAt, other.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "TimeDto{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
