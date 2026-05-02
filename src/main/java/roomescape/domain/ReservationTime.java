package roomescape.domain;

import roomescape.exception.Validator;

public class ReservationTime {
    private String startAt;

    public ReservationTime(String startAt) {
        Validator.validateTime(startAt);
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
