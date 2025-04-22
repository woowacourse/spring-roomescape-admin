package roomescape.entity;

import java.time.LocalTime;

public record TimeEntity(
        Long id,
        LocalTime startAt
) {
}
