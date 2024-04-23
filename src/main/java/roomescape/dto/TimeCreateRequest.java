package roomescape.dto;

import roomescape.domain.ReservationTime;

public record TimeCreateRequest(String startAt) {
    public ReservationTime createTime(long id) {
        return new ReservationTime(id, startAt);
    }
}
