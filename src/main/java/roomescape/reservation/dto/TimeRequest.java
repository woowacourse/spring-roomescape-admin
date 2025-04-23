package roomescape.reservation.dto;

import java.time.LocalTime;

public record TimeRequest(
        LocalTime startAt
) {
}
