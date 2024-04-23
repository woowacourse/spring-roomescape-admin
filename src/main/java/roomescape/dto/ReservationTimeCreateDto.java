package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeCreateDto {
    private String startAt;

    public ReservationTimeCreateDto() {
    }

    public ReservationTimeCreateDto(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toDomain() {
        LocalTime time = LocalTime.parse(startAt);
        return new ReservationTime(null, time);
    }

    public String getStartAt() {
        return startAt;
    }
}
