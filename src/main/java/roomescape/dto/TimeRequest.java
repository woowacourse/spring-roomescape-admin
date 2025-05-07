package roomescape.dto;

import roomescape.domain.Time;

public record TimeRequest(String startAt) {

    public Time toEntity() {
        return new Time(startAt);
    }
}
