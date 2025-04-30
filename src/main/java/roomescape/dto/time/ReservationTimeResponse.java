package roomescape.dto.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.domain.time.ReservationTime;

public class ReservationTimeResponse {

    private final long id;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    private ReservationTimeResponse(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt()
        );
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationTimeResponse that)) {
            return false;
        }
        return id == that.id && Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "ReservationTimeResponse{" +
            "id=" + id +
            ", startAt=" + startAt +
            '}';
    }
}
