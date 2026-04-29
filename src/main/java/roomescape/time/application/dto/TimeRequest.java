package roomescape.time.application.dto;

import java.time.LocalTime;

public record TimeRequest(
        LocalTime startAt
) {
}
