package roomescape.dto;

import java.util.Objects;
import roomescape.domain.reservationtime.ReservationTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final String startAt;

    private ReservationTimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(
                time.getId(),
                time.getStartAt().toStringTime()
        );
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
        ReservationTimeResponseDto other = (ReservationTimeResponseDto) o;
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
