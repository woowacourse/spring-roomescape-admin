package roomescape.dto.request;

import java.time.LocalTime;

public record TimeCreateRequest(
        LocalTime startAt
) {
}
