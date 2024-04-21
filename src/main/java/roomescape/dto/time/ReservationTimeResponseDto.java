package roomescape.dto.time;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeResponseDto {

    private final long id;
    private final String startAt;

    public ReservationTimeResponseDto(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt.toString();
    }

    public ReservationTimeResponseDto(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
