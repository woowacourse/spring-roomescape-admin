package roomescape.dto;

import roomescape.domain.Time;

public record TimeRequest(String startAt) {
    public Time toDomain(long id) {
        return new Time(id, startAt);
    }
}
