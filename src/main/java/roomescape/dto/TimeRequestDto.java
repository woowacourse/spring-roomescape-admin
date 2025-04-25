package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain_entity.ReservationTime;

public record TimeRequestDto(LocalTime startAt) {
    public ReservationTime toTime() {
        return new ReservationTime(startAt);
    }
}
