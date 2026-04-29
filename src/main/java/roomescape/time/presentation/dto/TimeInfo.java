package roomescape.time.presentation.dto;

import java.time.LocalTime;
import roomescape.time.repository.TimeEntity;

public record TimeInfo(
        Long id,
        LocalTime startAt
) {
    public static TimeInfo from(TimeEntity entity) {
        return new TimeInfo(
                entity.id(),
                entity.startAt().toLocalTime()
        );
    }
}
