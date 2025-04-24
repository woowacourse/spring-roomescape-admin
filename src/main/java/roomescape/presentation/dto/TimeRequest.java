package roomescape.presentation.dto;

import java.time.LocalTime;
import roomescape.business.domain.Time;

public record TimeRequest(LocalTime startAt) {

    public Time toDomain() {
        return new Time(startAt);
    }
}
