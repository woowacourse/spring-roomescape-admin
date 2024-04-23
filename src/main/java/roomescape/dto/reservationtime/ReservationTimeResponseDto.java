package roomescape.dto.reservationtime;

import java.util.Objects;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.StartAt;

public class ReservationTimeResponseDto {

    private final Long id;
    private final String startAt;

    private ReservationTimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        StartAt startAt = time.getStartAt();
        return new ReservationTimeResponseDto(
                time.getId(),
                startAt.toStringTime()
        );
    }

    public static ReservationTimeResponseDto of(Long id, String startAt) {
        return new ReservationTimeResponseDto(
                id,
                startAt
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
