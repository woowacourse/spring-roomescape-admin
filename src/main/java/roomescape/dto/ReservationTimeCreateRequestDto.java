package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Objects;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.StartAt;

public class ReservationTimeCreateRequestDto {

    private final String startAt;

    @JsonCreator
    private ReservationTimeCreateRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeCreateRequestDto from(String startAt) {
        return new ReservationTimeCreateRequestDto(startAt);
    }

    public static ReservationTimeCreateRequestDto from(ReservationTime reservationTime) {
        StartAt startAt = reservationTime.getStartAt();
        return new ReservationTimeCreateRequestDto(startAt.toStringTime());
    }

    public ReservationTime toDomain() {
        return ReservationTime.of(null, startAt);
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
        ReservationTimeCreateRequestDto other = (ReservationTimeCreateRequestDto) o;
        return Objects.equals(this.startAt, other.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt);
    }

    @Override
    public String toString() {
        return "ReservationTimeCreateRequestDto{" +
                "startAt='" + startAt + '\'' +
                '}';
    }
}
