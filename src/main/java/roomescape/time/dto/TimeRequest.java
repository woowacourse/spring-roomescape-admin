package roomescape.time.dto;

import java.time.LocalTime;
import roomescape.time.ReservationTime;

public record TimeRequest(LocalTime startAt) {
    public ReservationTime toDomain(Long id) {
        return new ReservationTime(id, startAt);
    }
}