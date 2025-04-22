package roomescape.domain;

import java.time.LocalTime;
import roomescape.dto.validation.ReservationTimeValidator;

public class ReservationTime {

    private final Long id;
    private final LocalTime time;

    private void validate() {
        boolean valid = new ReservationTimeValidator().isValid(this, null);
        if (!valid) {
            throw new IllegalArgumentException("유효하지 않은 예약시간입니다.");
        }
    }

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
        validate();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
