package roomescape.dto;

import roomescape.domain.ReservationTimeDto;

public record RequestTimes(String startAt) {

    public ReservationTimeDto toDomain() {
        return new ReservationTimeDto(null, startAt); // TODO null 을 받는 방식이 과연 옳을까??
    }
}
