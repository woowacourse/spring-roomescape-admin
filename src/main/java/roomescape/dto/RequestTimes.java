package roomescape.dto;

import roomescape.times.Times;

public record RequestTimes(String startAt) {

    public Times toDomain() {
        return new Times(null, startAt); // TODO null 을 받는 방식이 과연 옳을까??
    }
}
