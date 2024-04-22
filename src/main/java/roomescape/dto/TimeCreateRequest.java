package roomescape.dto;

import roomescape.domain.Time;

public record TimeCreateRequest(String startAt) {
    public Time createTime(long id) {
        return new Time(id, startAt);
    }
}
