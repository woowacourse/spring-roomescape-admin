package roomescape.time.presentation.dto;

import java.time.LocalTime;

public record TimeRequest(
        LocalTime startAt
) {
}
