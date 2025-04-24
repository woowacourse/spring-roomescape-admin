package roomescape.dto.response;

import java.time.LocalTime;

public record TimeResponse(
        Long id,
        LocalTime startAt
) {
}
